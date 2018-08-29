package br.ccasj.sisauc.administracao.formatter;

import br.ccasj.sisauc.administracao.beneficiario.domain.Beneficiario;

import javax.swing.text.MaskFormatter;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.Date;
import br.ccasj.sisauc.administracao.beneficiario.service.BeneficiarioService;

public class BeneficiarioFormatter implements SisaucFormatter<Beneficiario> {

    public static String toIdadeMedica(Beneficiario beneficiario) {
        if (beneficiario == null || beneficiario.getDataNascimento() == null) {
            return null;
        }
        Date dataNascimento = beneficiario.getDataNascimento();
        Date dataHoje = new Date();
        long diasNascimento = dataNascimento.getTime();
        long diasHoje = dataHoje.getTime();
        long diasVida = (diasHoje - diasNascimento) / 86400000L;
        int anos = (int) (diasVida / 365);
        int bissextos = (int) (anos / 6);
        int meses = (int) ((diasVida % 365) / 30);
        int dias = (int) (((diasVida % 365) % 30) - bissextos);
        return anos + " anos " + meses + " meses e " + dias + " dias";
    }

    public static String getSaramNomeCPF(Beneficiario beneficiario) {
        if (beneficiario == null) {
            return null;
        }
        final StringBuilder builder = new StringBuilder();

        builder.append(getSaramNome(beneficiario));
        if (StringUtils.isNotBlank(beneficiario.getCpf())){
        	builder.append(" - ").append(beneficiario.getCpf());
        }

        return builder.toString();
    }

    public static String getSaramNome(Beneficiario beneficiario) {
        if (beneficiario == null) {
            return null;
        }
        final StringBuilder builder = new StringBuilder();

        builder.append((beneficiario.getSaram() == null ? (beneficiario.getBeneficiarioTitular() != null ? beneficiario.getBeneficiarioTitular().getSaram() : beneficiario.getSaramTitular()) : beneficiario.getSaram()));
        builder.append(" - ").append(beneficiario.getNome());
        if (StringUtils.isNotBlank(beneficiario.getCpf())){
        	builder.append(" - ").append(beneficiario.getCpf());
        }

        return builder.toString();
    }
    public static String getSaramTitular(Beneficiario beneficiario) {
        if (beneficiario != null) {
            if (beneficiario.getSaram() != null) {
                return beneficiario.getSaram();
            } else {
                return (beneficiario.getBeneficiarioTitular() != null) ? beneficiario.getBeneficiarioTitular().getSaram() : beneficiario.getSaramTitular();
            }
        } else {
            return null;
        }
    }

    public static String getCpfFormatado(Beneficiario beneficiario) {
        String cpfFormatado = "";
        if (beneficiario != null && beneficiario.getCpf() != null) {
            try {
                cpfFormatado = format("###.###.###-##", beneficiario.getCpf());

            } catch (Exception e) {
                cpfFormatado = beneficiario.getCpf();
            }
        }
        return cpfFormatado;
    }

    public static String getCpfFormatadoTitular(Beneficiario beneficiarioTitular) {
        String cpfFormatado = "";
        if (beneficiarioTitular.getBeneficiarioTitular() != null && beneficiarioTitular.getBeneficiarioTitular().getCpf() != null) {
            try {
                cpfFormatado = format("###.###.###-##", beneficiarioTitular.getBeneficiarioTitular().getCpf());
            } catch (Exception e) {
                cpfFormatado = beneficiarioTitular.getBeneficiarioTitular().getCpf();
            }
        } else if (beneficiarioTitular.getCpf() != null) {
            try {
                cpfFormatado = format("###.###.###-##", beneficiarioTitular.getCpf());
            } catch (Exception e) {
                cpfFormatado = beneficiarioTitular.getCpf();
            }
        }
        return cpfFormatado;
    }

    private static String format(String pattern, Object value) {
        MaskFormatter mask;
        try {
            mask = new MaskFormatter(pattern);
            mask.setValueContainsLiteralCharacters(false);
            return mask.valueToString(value);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getAutocompleteLabel(Beneficiario object) {
        return getSaramNomeCPF(object);
    }

}
