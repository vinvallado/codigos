package br.ccasj.sisauc.administracao.cadastro.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ccasj.sisauc.administracao.cadastro.dao.ArquivoDAO;
import br.ccasj.sisauc.administracao.cadastro.domain.Arquivo;
import br.ccasj.sisauc.administracao.cadastro.service.ArquivoService;
import br.ccasj.sisauc.framework.exception.SisaucException;
import br.ccasj.sisauc.framework.exception.SystemRuntimeException;

@Transactional
@Service(value = "arquivoService")
public class ArquivoServiceImpl implements ArquivoService {

	@Autowired
	private ArquivoDAO arquivoDAO;
	
	@Autowired
	private String arquivosDir;

	@Override
	public Arquivo inserirArquivoNoSistema(Arquivo arquivo, Integer idOM, Date dataInsercao) {
		try {
			verificarArquivo(arquivo);
			montarArquivo(arquivo, idOM, dataInsercao);
		} catch (IOException e) {
			throw new SystemRuntimeException(e.getMessage());
		}

		Arquivo arquivoSalvo = arquivoDAO.merge(arquivo);
		arquivoSalvo.setFile(arquivo.getFile());
		return arquivoSalvo;
	}

	private void verificarArquivo(Arquivo arquivo) throws IOException {
		if (arquivo == null || arquivo.getStream() == null || arquivo.getStream().available() <= 0) {
			throw new SystemRuntimeException("Falha ao inserir arquivo: Arquivo corrompido ou inexistente");
		}
	}

	@Override
	public File obterArquivo(Arquivo arquivo, Integer idOM, Date dataInsercao) {
		String nomeDiretorio = montarNomeDiretorio(arquivo, idOM);
		String nomeArquivoSisauc = montarNomeArquivo(arquivo, dataInsercao);
		
		File diretorio = new File(nomeDiretorio);
		if (!diretorio.exists()) {
			if(!diretorio.mkdirs()){
				throw new SecurityException("Não foi possível criar um diretório ao obter o arquivo");
			}
		}

		File file = new File(diretorio, nomeArquivoSisauc);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new SystemRuntimeException("Falha ao obter arquivo: Arquivo corrompido ou inexistente");
			}
		}
		return file;
	}

	private String montarNomeArquivo(Arquivo arquivo, Date dataInsercao) {
		String timestamp = String.valueOf(dataInsercao.getTime());
		return timestamp.concat(".sisauc");
	}

	private String montarNomeDiretorio(Arquivo arquivo, Integer idOM) {
		String data = new SimpleDateFormat("yyyy/MM").format(arquivo.getDataInsercao());
		return new StringBuilder().append(arquivosDir).append("/").append(String.valueOf(idOM)).append("/").append(data).toString();
	}

	private void montarArquivo(Arquivo arquivo, Integer idOM, Date dataInsercao) throws IOException {
		File file = obterArquivo(arquivo, idOM, dataInsercao);
		salvarStreamNoArquivo(arquivo.getStream(), file);
		arquivo.setFile(file);
	}

	private void salvarStreamNoArquivo(InputStream stream, File file) throws IOException {
		if (stream.available() <= 0) {
			return;
		}
		OutputStream out = new FileOutputStream(file);
		byte buf[] = new byte[1024];
		int len;
		while ((len = stream.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		out.close();
		stream.close();
	}
	
	
	public void gerarRespostaDownload(File arquivo, String nomeReal, HttpServletResponse response) throws SisaucException, IOException {

		setarAtributosResposta(response, nomeReal);
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(arquivo);
		} catch (FileNotFoundException e) {
			throw new SisaucException("Arquivo não encontrado!");
		}

		int val;
		ServletOutputStream outputStream = response.getOutputStream();
		while ((val = inputStream.read()) != -1) {
			outputStream.write(val);
		}
		outputStream.close();
		inputStream.close();
	}	

	private static void setarAtributosResposta(HttpServletResponse response, String nomeArquivo) {
		
		nomeArquivo = nomeArquivo.replace(" ", "_");
		
		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "inline; filename=\"" + nomeArquivo + "\"");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "No-cache");
	}	

}
