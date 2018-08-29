package br.ccasj.sisauc.intendencia.domain.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;
import br.ccasj.sisauc.aerconsig.service.AerconsigResultado;
import br.ccasj.sisauc.aerconsig.service.AerconsigService;
import br.ccasj.sisauc.auditoriaretrospectiva.dao.impl.ItemGABToView;
import br.ccasj.sisauc.auditoriaretrospectiva.domain.ParametrosRelatorioDescontoBeneficiariosPesquisa;
import br.ccasj.sisauc.emissaogab.dao.ItemGABDAO;
import br.ccasj.sisauc.emissaogab.domain.GuiaApresentacaoBeneficiario;
import br.ccasj.sisauc.emissaogab.domain.ItemGAB;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;
import br.ccasj.sisauc.intendencia.dao.DescontoBeneficiarioDAO;
import br.ccasj.sisauc.intendencia.domain.DescontoBeneficiario;
import br.ccasj.sisauc.intendencia.domain.EnvioDesconto;
import br.ccasj.sisauc.intendencia.domain.EstadoRelatorioFolhaBeneficiario;
import br.ccasj.sisauc.intendencia.domain.EstadoEnvioFolhaPagamento;
import br.ccasj.sisauc.intendencia.domain.RelatorioDescontoBeneficiario;
import br.ccasj.sisauc.intendencia.domain.RelatorioDescontoBeneficiarioItem;
import br.ccasj.sisauc.intendencia.domain.service.DescontoBeneficiariosService;

@Service(value = "descontoBeneficiariosService")
public class DescontoBeneficiariosServiceImpl implements DescontoBeneficiariosService {

	private static final double FATOR_DESCONTO_BENEFICIARIO = 0.2;//Fator de desconto de 20%
	
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private DescontoBeneficiarioDAO descontoBeneficiarioDAO;
	
	@Autowired
	private ItemGABDAO itemGabDAO;
	
	@Autowired
	private AerconsigService aerconsigService; 
	
	private Beneficiario beneficiarioTitularAtual;
	
	@Override
	public List<ItemGAB> pesquisa(ParametrosRelatorioDescontoBeneficiariosPesquisa parametros) {
		return itemGabDAO.obterItensGabParaDesconto(parametros);
	}

	@Override
	public void salvar(List<ItemGAB> itensGAB) {
		HashMap<Beneficiario, DescontoBeneficiario> descontos = new HashMap<Beneficiario, DescontoBeneficiario>();
		for (ItemGAB itemGab : itensGAB) {
			ItemGAB itemGABCompleto = itemGabDAO.findById(itemGab.getId());
			
			Beneficiario beneficiario = itemGABCompleto.getGab().getBeneficiario();
			Beneficiario beneficiarioTitular = null;
			if (beneficiario.isTitular()) {
				beneficiarioTitular = beneficiario;
			} else {
				beneficiarioTitular = beneficiario.getBeneficiarioTitular();
			}
			
			DescontoBeneficiario desconto = descontos.get(beneficiarioTitular);
			if (desconto == null) {
				desconto = new DescontoBeneficiario();
				desconto.setBeneficiario(beneficiarioTitular);
				desconto.setEstadoDescontoBeneficiario(EstadoRelatorioFolhaBeneficiario.INTERROMPIDO_ERRO);
				descontos.put(beneficiarioTitular, desconto);
			}
			
			desconto.addItensGabDescontado(itemGABCompleto);
		}
		
		Iterator<DescontoBeneficiario> iteratorDescontos = descontos.values().iterator();
		while (iteratorDescontos.hasNext()) {
			DescontoBeneficiario desconto = iteratorDescontos.next();
			
			double valorTotal = obterValorTotal(desconto.getItensGabDescontados());
			desconto.setValorTotal(valorTotal);
			if (Double.compare(valorTotal,0.0) == 0) {
				desconto.setEstadoDescontoBeneficiario(EstadoRelatorioFolhaBeneficiario.ENVIADO);
			}
			
			criarNumeracao(desconto);
			descontoBeneficiarioDAO.merge(desconto);
		}
		
	}

	private double obterValorTotal(Set<ItemGAB> itensGAB) {
		double valorTotal = 0;
		for (ItemGAB itemGAB : itensGAB) {
			GuiaApresentacaoBeneficiario gab = itemGAB.getGab();
			if (!gab.isIsento()) {
				valorTotal += (itemGAB.getAuditoriaRetrospectiva().getValorFinal() * FATOR_DESCONTO_BENEFICIARIO);	
			}
			
		}
		return valorTotal;
	}

	public Set<ItemGAB> converterParaItensGab(List<ItemGABToView> itensGabToView) {
		HashSet<ItemGAB> itensGab = new HashSet<ItemGAB>();
		for (ItemGABToView itemGABToView : itensGabToView) {
			itensGab.add(itemGABToView.getItemGAB());
		}
		return itensGab;
	}

	private void criarNumeracao(DescontoBeneficiario desconto) {
		if (desconto.getId() == null) {
			Calendar calendar = Calendar.getInstance();
			StringBuilder builder = new StringBuilder();
			Integer ano = Integer.valueOf(calendar.get(Calendar.YEAR));
			int quantidade = descontoBeneficiarioDAO.obterQuantidadeDescontosPorAnoEBeneficiario(desconto
					.getBeneficiario().getId(), ano) + 1;
			builder.append("DB").append(String.valueOf(ano)).append("/").append(desconto.getBeneficiario().getSaram())
					.append("/").append(StringUtils.leftPad(String.valueOf(quantidade), 5, "0"));
			desconto.setNumero(builder.toString());
			desconto.setDataEmissao(new Date());
		}
	}

	@Override
	public void verificarBeneficiarios(List<ItemGAB> itensSelecionados){
		beneficiarioTitularAtual = null;

		for (ItemGAB item : itensSelecionados) {
			if (beneficiarioTitularAtual == null) {
				if (item.getGab().getBeneficiario().getBeneficiarioTitular() != null) {
					beneficiarioTitularAtual = item.getGab().getBeneficiario().getBeneficiarioTitular();
				} else {
					beneficiarioTitularAtual = item.getGab().getBeneficiario();
				}
			}
			if (item.getGab().getBeneficiario().getBeneficiarioTitular() != null) {
				if (beneficiarioTitularAtual != item.getGab().getBeneficiario()) {
					throw new SystemRuntimeException(
							"É necessário que todos os itens pertençam a um mesmo beneficiario titular");
				}
			}
			if (beneficiarioTitularAtual != item.getGab().getBeneficiario()) {
				throw new SystemRuntimeException(
						"É necessário que todos os itens pertençam a um mesmo beneficiario titular");
			}
		}
	}

	@Override
	public void verificarEnvioDescontos() {
		Calendar calendarDate = Calendar.getInstance();
		calendarDate.setTime(new Date());
		
		int minutos = calendarDate.get(Calendar.MINUTE);
		//Envia os descontos a cada hora fechada
		if (minutos == 0) {
			enviarDescontos();
		}
		
	}
	
	private void enviarDescontos() {
		List<DescontoBeneficiario> descontosNaoEnviados = descontoBeneficiarioDAO.obterDescontosNaoEnvidaos();
		int sessaoEnvio = obterSessaoEnvio();
		
		for (DescontoBeneficiario descontoBeneficiario : descontosNaoEnviados) {
			Beneficiario beneficiario = descontoBeneficiario.getBeneficiario();
			EnvioDesconto envio = new EnvioDesconto();
			AerconsigResultado resultadoEnvio = null;
			
			envio.setDataHoraInicioEnvio(new Date());
			resultadoEnvio = aerconsigService.enviarDesconto(beneficiario.getCpf(), 
					beneficiario.getSaram(), descontoBeneficiario.getValorTotal(), sessaoEnvio);
			envio.setCodigoMensagem(resultadoEnvio.getCodigoRetorno());
			
			envio.setDataHoraFimEnvio(new Date());
			envio.setDescricaoMensagem(resultadoEnvio.getMensagemRetorno());
			
			if (resultadoEnvio.isSucesso()) {
				envio.setEstadoEnvioDesconto(EstadoEnvioFolhaPagamento.ENVIADO);
				descontoBeneficiario.setEstadoDescontoBeneficiario(EstadoRelatorioFolhaBeneficiario.ENVIADO);
			} else {
				if (verificarCodigoRetornoInterrompe(resultadoEnvio.getCodigoRetorno())) {
					descontoBeneficiario.setEstadoDescontoBeneficiario(EstadoRelatorioFolhaBeneficiario.INTERROMPIDO_ERRO);
				}
				envio.setEstadoEnvioDesconto(EstadoEnvioFolhaPagamento.ERRO);
			}
			
			descontoBeneficiario.addEnvioDesconto(envio);
			this.descontoBeneficiarioDAO.merge(descontoBeneficiario);
		}
		
	}

	private boolean verificarCodigoRetornoInterrompe(String codigoRetornoStr) {
		Integer codigoRetorno = Integer.valueOf(codigoRetornoStr);
		boolean retorno;
		if (codigoRetorno >= 901 && codigoRetorno <= 999) {
			//Códigos entre 901 e 997 são erros do sistema de configuração, de programação, do 
			//servidor de aplicação ou do sistema de arquivo
			//999 - Código de erro para eventuais mensagens não mapeadas
			//Tentar novamente o envico
			retorno = false;
		} else if (codigoRetorno < 0) {
			//Código de retorno < 0 indica que foi um erro do Java e não da aplicação.
			retorno = false;
		} else {
			//Demais códigos devem interromper o envio.
			retorno = true;
		}
		return retorno;
	}
	
	private int obterSessaoEnvio() {
		return (int) (Math.random()*10000);
	}
	
	@Override
	public double calcularValorDesconto(double valorTotal) {
		return valorTotal * FATOR_DESCONTO_BENEFICIARIO;
	}

	@Override
	public InputStream obterStreamRelatorioXLS(RelatorioDescontoBeneficiario relatorio) throws IOException {
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("new sheet");
		
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("Saram Titular");
		row.createCell(1).setCellValue("Beneficiário Titular");
		row.createCell(2).setCellValue("Posto/Graduação Titular");
		row.createCell(3).setCellValue("OM Titular");
		row.createCell(4).setCellValue("AMH/AMHC");
		row.createCell(5).setCellValue("Beneficiário");
		row.createCell(6).setCellValue("Item da GAB");
		row.createCell(7).setCellValue("Valor a descontar");
		
		int rowIndex = 1;
		for (RelatorioDescontoBeneficiarioItem item: relatorio.getItens()){
			GuiaApresentacaoBeneficiario gab = item.getItemGab().getGab();
			Beneficiario beneficiario = gab.getBeneficiario();
			Beneficiario beneficiarioTitular = beneficiario.getBeneficiarioTitular();
			
			row = sheet.createRow(rowIndex++);
			row.createCell(0).setCellValue(beneficiarioTitular!=null ? beneficiarioTitular.getSaram() : beneficiario.getSaram());
			row.createCell(1).setCellValue(beneficiarioTitular!=null ? beneficiarioTitular.getNome() : beneficiario.getNome());
			
			if(beneficiario.getPostoGraduacao() == null){
				if(beneficiarioTitular != null){
					if(beneficiarioTitular.getPostoGraduacao() == null){
						row.createCell(2).setCellValue("");
					}else{
						row.createCell(2).setCellValue(beneficiarioTitular.getPostoGraduacao().getSigla());
					}
				}
				if(beneficiarioTitular == null){
					row.createCell(2).setCellValue("");
				}
				
			}else{
				row.createCell(2).setCellValue(beneficiario.getPostoGraduacao().getSigla());
			}
			
			if(beneficiario.getOrganizacaoMilitar() == null){
				if(beneficiarioTitular != null){
					if(beneficiarioTitular.getOrganizacaoMilitar() == null){
						row.createCell(3).setCellValue("");
					}else{
						row.createCell(3).setCellValue(beneficiarioTitular.getOrganizacaoMilitar().getSigla());
					}
				}
				if(beneficiarioTitular == null){
					row.createCell(3).setCellValue("");
				}
				
			}else{
				row.createCell(3).setCellValue(beneficiario.getOrganizacaoMilitar().getSigla());
			}
			
			row.createCell(4).setCellValue(beneficiario.getConvenio().getSigla());
			row.createCell(5).setCellValue(beneficiario.getNome());
			row.createCell(6).setCellValue(item.getItemGab().getGab().getCodigo());
			row.createCell(7).setCellValue(item.getItemGab().getValorADescontar());
		}
		
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);
		sheet.autoSizeColumn(6);
		sheet.autoSizeColumn(7);
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle cs = workbook.createCellStyle();
		cs.setDataFormat(workbook.createDataFormat().getFormat("[$R$-416] #.##0,00;[RED]-[$R$-416] #.##0,00"));
		sheet.setDefaultColumnStyle(7, cs);
		
		File fileOut = File.createTempFile("relatorio", ".xls");
		FileOutputStream fos = new FileOutputStream(fileOut);
	    wb.write(fos);
	    fos.close();
	    
	    File fileIn = File.createTempFile("relatorio-in", ".xls");
	    FileUtils.copyFile(fileOut, fileIn);
	    return new FileInputStream(fileIn);
	}
}