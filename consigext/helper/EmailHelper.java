package br.mil.fab.consigext.helper;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import br.mil.fab.consigext.entity.Consignacao;
import br.mil.fab.consigext.entity.EntidadeConsig;
import br.mil.fab.consigext.entity.Organizacao;
import br.mil.fab.consigext.entity.PesfisComgep;
import br.mil.fab.consigext.entity.SolicitacaoSaldoDevedor;
import br.mil.fab.consigext.util.GenericUtil;

public class EmailHelper {

	private Properties properties;
	private String from = "noreply@fab.mil.br";
	public final static int RECOVER = 1;
	public final static int PRIMEIRO_ACESSO = 0;
	public final static String finalMessage = "<br/><p>Caso haja algum questionamento ou dúvida entre em contato com o SAUTI pelo Tel: (21) 2101-7902 – <a href='http://www.sauti.intraer' >http://www.sauti.intraer</a> .</p>"+
    	"<p>Atenciosamente, </p>" + "<p>Centro de Computação da Aeronáutica do Rio de Janeiro</p>";;
	public EmailHelper() {
		super();
		properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.host", "smtp.mail.intraer");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.timeout", 10000);
	}

	private void sendMail(String subject, String message, String to) throws MessagingException {
		if(to==null || to.isEmpty())
			return; 
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, "Abc@123456");
			}
		});

		MimeMessage mmessage = new MimeMessage(session);
		mmessage.setFrom(new InternetAddress(from));
		mmessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		mmessage.setSubject(subject);
		mmessage.setContent(message, "text/html; charset=utf-8");
		Transport.send(mmessage);
	}

	private void sendMailWithAttachment(File file, String subject, String message, String to, String fileName)
			throws MessagingException {
		if(to==null || to.isEmpty())
			return; 
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, "Abc@123456");
			}
		});

		MimeMessage mmessage = new MimeMessage(session);
		mmessage.setFrom(new InternetAddress(from));
		mmessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		mmessage.setSubject(subject);
		// mmessage.setContent(message, "text/html; charset=utf-8");
		// Transport.send(mmessage);

		// ---------------------------------------------
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html; charset=utf-8");
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		messageBodyPart = new MimeBodyPart();
		DataSource source = new FileDataSource(file);
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(fileName);
		multipart.addBodyPart(messageBodyPart);
		mmessage.setContent(multipart, "text/html; charset=utf-8");
		Transport.send(mmessage);
	}
	
	private void sendMailWithArrayAttachments(File[] files, String subject, String message, String to, String[] fileNames)
			throws MessagingException {
		if(to==null || to.isEmpty())
			return; 
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, "Abc@123456");
			}
		});
		MimeMessage mmessage = new MimeMessage(session);
		mmessage.setFrom(new InternetAddress(from));
		mmessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		mmessage.setSubject(subject);
		// ---------------------------------------------
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html; charset=utf-8");
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		// ---------------------------------------------
		for(int i=0;i<files.length;i++) {
			File file = files[i];
			String fileName = fileNames[i];
			if(file==null)
				continue;
			messageBodyPart = new MimeBodyPart();
			messageBodyPart.setDataHandler(new DataHandler(new FileDataSource(file)));
			messageBodyPart.setFileName(fileName);
			multipart.addBodyPart(messageBodyPart);
		}
		mmessage.setContent(multipart, "text/html; charset=utf-8");
		Transport.send(mmessage);
	}

	
	public void sendReativacaoConsignacao(long ADE, String to, File file, String motivo, String observacao,
			String nameOfFile) {
		String subject = "Reativação de Consignação";
		String message = "<p>Prezado usuário, </p>" + "<p>Sua consignacao referente a ADE: <b>" + ADE
				+ "</b> foi reativada.</p>" + "<p>Motivo da reativação: " + motivo + "</p>" + "<p>Observações: "
				+ observacao + "</p>";
		message+= finalMessage;
		if (file != null) {
			message += "<br/><p>Anexo: " + nameOfFile + "</p>";
			try {
				sendMailWithAttachment(file, subject, message, to, nameOfFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				sendMail(subject, message, to);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void sendSuspensaoConsignacao(long ADE, String to, File file, String motivo, String observacao,
			String nameOfFile) {
		String subject = "Suspensão de Consignação";
		String message = "<p>Prezado usuário/entidade consignatária, </p>" + "<p>Sua consignacao referente a ADE: <b>" + ADE
				+ "</b> foi suspensa.</p>" + "<p>Motivo da reativação: " + motivo + "</p>" + "<p>Observações: "
				+ observacao + "</p>";
		message+= finalMessage;
		
		if (file != null) {
			message += "<br/><p>Anexo: " + nameOfFile + "</p>";
			try {
				sendMailWithAttachment(file, subject, message, to, nameOfFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				sendMail(subject, message, to);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	

	public void sendLiquidacaoContrato(long ADE, String to, File file, String motivo, String observacao,
			String nameOfFile) {
		String subject = "Liquidação de Contrato";
		String message = "<p>Prezado usuário, </p>" + "<p>Sua consignacao referente a ADE: <b>" + ADE
				+ "</b> foi liquidada.</p>" + "<p>Motivo da liquidação: " + motivo + "</p>" + "<p>Observações: "
				+ observacao + "</p>";
		if (file != null) {
			message += "<p>Anexo: " + nameOfFile + "</p>";
			try {
				sendMailWithAttachment(file, subject, message, to, nameOfFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				sendMail(subject, message, to);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendDesliquidacaoContrato(long ADE, String to, File file, String motivo, String observacao,
			String nameOfFile) {
		String subject = "Deliquidação de Contrato";
		String message = "<p>Prezado usuário, </p>" + "<p>Sua consignacao referente a ADE: <b>" + ADE
				+ "</b> foi desliquidada.</p>" + "<p>Motivo da desliquidação: " + motivo + "</p>" + "<p>Observações: "
				+ observacao + "</p>";
		if (file != null) {
			message += "<p>Anexo: " + nameOfFile + "</p>";
			try {
				sendMailWithAttachment(file, subject, message, to, nameOfFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				sendMail(subject, message, to);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	public void sendCodigoUnico(String to, String uid, String cod, String validade) throws MessagingException {
		String subject = "Código Único - Consignações";
		String message = "<p>Prezado usuário, </p>"
				+ "<p>Recebemos sua solicitação de criação de código único referente ao CPF " + uid
				+ " que está associado a esse email.</p>" + "<p>Seu código de único é: </p>" + "<p><b>" + cod
				+ "</b></p>" + "<p>A data de validade deste código é: </p>" + "<p><b>" + validade + "</b></p>";
		message+=finalMessage;
		sendMail(subject, message, to);
	}

	public void sendPasswordUsuarioExterno(String to, String user, String cpf, String pass, Date dtValidade)
			throws MessagingException {
		String subject = "Novo Usuário - Consignação Externa";
		String message = "<p>Prezado usuário, </p>" + "<p>O usuário <b>" + user + "</b> associado ao CPF <b>" + cpf
				+ "</b> foi criado com sucesso.</p>" + "<p>A senha de acesso é: </p>" + "<p><b>" + pass + "</b></p>"
				+ "<p>Com validade até: </p>" + "<p><b>" + dtValidade.toString() + "</b></p>";
		message += finalMessage;
		sendMail(subject, message, to);
	}

	public void sendLiquidacaoParcelas(Long ADE, String to, String motivo, HashMap<Long, String> parcelasMap) {
		String subject = "Liquidação de Parcelas";
		String message = "<p>Prezado usuário/entidade consignatária, </p>" + "<p>Parcelas foram liquidadas referentes a sua consignacao de ADE: <b>" + ADE
				+ "</b>.</p>";
		if(motivo.trim().isEmpty()==false)
			message+= "<p>Motivo da liquidação: <b>" + motivo + "</b></p>";
	    message+="<p><b>Parcelas liquidadas: </b></p>";
		for (Map.Entry<Long,String> pair : parcelasMap.entrySet()) {
		    message+="<p>";
		    message+="Parcela "+pair.getKey()+pair.getValue();
		    message+="</p>";		
		}	    
	    message+= finalMessage;
		try {
			sendMail(subject, message, to);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void sendDesliquidacaoParcela(Long ADE, String to, String motivo, String desliquidacaoEmail) {
		String subject = "Desliquidação de Parcela";
		String message = "<p>Prezado usuário/entidade consignatária, </p>" + "<p>Uma parcela foi desliquidada referente a sua consignacao de ADE: <b>" + ADE
				+ "</b>.</p>";
		if(motivo.trim().isEmpty()==false)
			message+= "<p>Motivo da desliquidação: <b>" + motivo + "</b></p>";		
	    message+="<p>" + desliquidacaoEmail + "</p>";				    
	    message+= finalMessage;
		try {
			sendMail(subject, message, to);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void sendSolicitacaoSaldoDevedor(Consignacao consignacao, String consOuLiquid, String descSolic) {
		String subject = descSolic;
		String message = "<p>Sistema Digital de Consignações: O saldo devedor da consignacao N. "+ consignacao.getNrAde();
		message+=" foi solicitado com propósito de <strong>";
		message+= consOuLiquid.equals("0") ? "CONSULTA" : "LIQUIDAÇÃO";
		message+= "</strong> do contrato.</p>";
	    message+="<br><p>Abaixo seguem os dados desta consignação:</p><br>";				    
		message+=dadosDaConsignacao(consignacao);
	    message+= finalMessage;
		try {
			EntidadeConsig entidadeConsig = consignacao.getEntidadeConsig();
			sendMail(subject, message, entidadeConsig.getDsEmailEc());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public String dadosDaConsignacao(Consignacao consignacao) {
		String message="<p><strong>Consignante:</strong> AGC E-CONSIG</p>";
		EntidadeConsig entidadeConsig = consignacao.getEntidadeConsig();
		Organizacao org = entidadeConsig.getOrganizacao();
		message+="<p><strong>Consignatária:</strong> "+org.getNmOrg()+" ("+org.getSgOrg()+")"+"</p>";
		PesfisComgep pesfis = consignacao.getServidorConsig().getPesfis();
		message+="<p><strong>Militar/Pensionista:</strong> "+pesfis.getNrOrdem()+" - "+pesfis.getNmPessoa()+"</p>";
		message+="<p><strong>CPF:</strong> "+pesfis.getNrCpf()+"</p>";
		message+="<p><strong>Data de inclusão do contrato:</strong> "+GenericUtil.dataParaString(consignacao.getDtReserva(), "dd/MM/yyyy HH:mm:ss")+"</p>";
		message+="<p><strong>N. ADE:</strong> "+consignacao.getNrAde()+"</p>";
		message+="<p><strong>Identificador:</strong> "+consignacao.getDsIdentificador()+"</p>";
		message+="<p><strong>Serviço:</strong> "+consignacao.getServicoConsig().getServico().getDsServico()+"</p>";
		message+="<p><strong>Vlr. prest. (R$):</strong> "+GenericUtil.printNumberTwoDigits(consignacao.getVlPrestacao())+"</p>";
		message+="<p><strong>N. prest.:</strong> "+consignacao.getNrPrestacoes()+"</p>";
		message+="<p><strong>Parcelas pagas:</strong> "+consignacao.getParcelasPagas().size()+"</p>";
		message+="<p><strong>Situação da ADE:</strong> "+consignacao.getStatusConsignacao().getNmStatus()+"</p>";
		return message;
	}
	public String sendSaldoDevedorAndReturnError(SolicitacaoSaldoDevedor solic, String to, Object[] redirect_idFile_file_Demonstrativo,
			Object[] redirect_idFile_file_Boleto, Object[] redirect_idFile_file_Detalhes) {
		Consignacao consignacao = solic.getConsignacao();
		String subject = "O saldo devedor da consignação N. "+ consignacao.getNrAde()+" foi informado.";
		File file_demonstrativo = (File)redirect_idFile_file_Demonstrativo[2];
		File file_Boleto = (File)redirect_idFile_file_Boleto[2];
		File file_Detalhes = (File)redirect_idFile_file_Detalhes[2];
		String message = "<p>Sistema Digital de Consignações: O saldo devedor da consignacao N. "+ consignacao.getNrAde()+"</p>";
		message+="<br><p>Abaixo seguem os dados desta consignação:"+"</p><br>";
		message+=dadosDaConsignacao(consignacao);
		message+="<br><p>Abaixo seguem os dados do saldo devedor</p><br>";
		message+="<p><strong>Valor do saldo devedor (R$): </strong>"+ GenericUtil.printNumberTwoDigits(solic.getVlSaldoDevedor())+"</p>";
		message+="<p><strong>Observação: </strong>"+ solic.getObservacaoDeposito()+"</p>";
		File[] files = new File[3];
		String[] nameOfFiles = new String[3];
		if(file_demonstrativo!=null) {
			message+="<p><strong>Demonstrativo do Cálculo: </strong> "+redirect_idFile_file_Demonstrativo[3]+ " em anexo</p>";
			files[0]=(File)redirect_idFile_file_Demonstrativo[2];
			nameOfFiles[0] = (String)redirect_idFile_file_Demonstrativo[3];
		}
		else {
			files[0]=null;
			nameOfFiles[0] = null;
		}
		if(file_Boleto!=null) {
			message+="<p><strong>Boleto: </strong>"+redirect_idFile_file_Boleto[3]+ " em anexo</p>";
			files[1]=(File)redirect_idFile_file_Boleto[2];
			nameOfFiles[1] = (String)redirect_idFile_file_Boleto[3];
		}
		else {
			files[1]=null;
			nameOfFiles[1] = null;
		}
		if(file_Detalhes!=null) {
			message+="<p><strong>Detalhes: </strong>"+redirect_idFile_file_Detalhes[3]+ " em anexo</p>";
			files[2]=(File)redirect_idFile_file_Detalhes[2];
			nameOfFiles[2] = (String)redirect_idFile_file_Detalhes[3];
		}
		else {
			files[2]=null;
			nameOfFiles[2] = null;
		}
	    message+= finalMessage;
	    try {
		    sendMailWithArrayAttachments(files, subject, message, to, nameOfFiles);
		    return null;
		}
		catch(Exception e) {
			e.printStackTrace();
			return "sendSaldoDevedor.erro.saldoDevedor.email";
		}
	}
}