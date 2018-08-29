package br.mil.fab.consigext.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.nio.file.Files;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.mil.fab.consigext.config.SambaProperties;
import br.mil.fab.consigext.exceptions.SambaException;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

@Component
public class SambaUtil {
	
	@Autowired
	private SambaProperties props;

	public String gravarDocumento(String dir, File arquivo) throws SambaException {
		try {
			NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("", props.getUser(), 
					props.getPass());
			SmbFile pathSMB = new SmbFile(props.getAddress() + dir, auth);
			pathSMB.connect();
		    SmbFileOutputStream smbFOS = new SmbFileOutputStream(pathSMB, !pathSMB.exists());
			smbFOS.write(Files.readAllBytes(arquivo.toPath()));
			smbFOS.flush();
			smbFOS.close();
			return pathSMB.getCanonicalPath();
		} catch (UnknownHostException e) {
			throw new SambaException("Erro no servidor de imagens(SAMBA): " + e.getMessage(), e);
		} catch (MalformedURLException e) {
			throw new SambaException("Erro no servidor de imagens(SAMBA): " + e.getMessage(), e);
		} catch (SmbException e) {
			throw new SambaException("Erro no servidor de imagens(SAMBA): " + e.getMessage(), e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new SambaException("Erro no servidor de imagens(SAMBA): " + e.getMessage(), e);
		}
	}
	
	
	public String gravarDocumento(String dir, byte[] arquivo) throws SambaException {
		try {
			NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("", props.getUser(), 
					props.getPass());
			SmbFile pathSMB = new SmbFile(props.getAddress() + dir, auth);
			pathSMB.connect();
		    SmbFileOutputStream smbFOS = new SmbFileOutputStream(pathSMB, !pathSMB.exists());
			smbFOS.write(arquivo);
			smbFOS.flush();
			smbFOS.close();
			return pathSMB.getCanonicalPath();
		} catch (UnknownHostException e) {
			throw new SambaException("Erro no servidor de imagens(SAMBA): " + e.getMessage(), e);
		} catch (MalformedURLException e) {
			throw new SambaException("Erro no servidor de imagens(SAMBA): " + e.getMessage(), e);
		} catch (SmbException e) {
			throw new SambaException("Erro no servidor de imagens(SAMBA): " + e.getMessage(), e);
		} catch (IOException e) {
			throw new SambaException("Erro no servidor de imagens(SAMBA): " + e.getMessage(), e);
		}
	}

	public byte[] download(String caminho) {
		InputStream in = null;
		ByteArrayOutputStream out = null;
		NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("", props.getUser(), 
				props.getPass());
		try {
			SmbFile remoteFile = new SmbFile(caminho, auth);
			remoteFile.connect();
			in = new BufferedInputStream(new SmbFileInputStream(remoteFile));
			out = new ByteArrayOutputStream((int) remoteFile.length());
			// Read the contents of the documents
			byte[] buffer = new byte[4096];
			int len = 0; // Read length
			while ((len = in.read(buffer, 0, buffer.length)) != -1) {
				out.write(buffer, 0, len);
			}
			out.flush();
			return out.toByteArray();
		} catch (Exception e) {
			Logger.getLogger(SambaUtil.class.getName()).error(e.getStackTrace());
			return null;
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				Logger.getLogger(SambaUtil.class.getName()).error(e.getStackTrace());
				return null;
			}
		}
		
	}
	


}
