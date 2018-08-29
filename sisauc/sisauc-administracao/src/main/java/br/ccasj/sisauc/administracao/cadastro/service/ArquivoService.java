package br.ccasj.sisauc.administracao.cadastro.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import br.ccasj.sisauc.administracao.cadastro.domain.Arquivo;
import br.ccasj.sisauc.framework.exception.SisaucException;



public interface ArquivoService {
	public Arquivo inserirArquivoNoSistema(Arquivo arquivo, Integer idOM, Date dataInsercao);
	public File obterArquivo(Arquivo arquivo, Integer idOM, Date dataInsercao);
	public void gerarRespostaDownload(File arquivo, String nomeReal, HttpServletResponse response) throws SisaucException, IOException;
}
