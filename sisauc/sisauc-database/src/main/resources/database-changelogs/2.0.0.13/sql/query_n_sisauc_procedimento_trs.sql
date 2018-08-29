UPDATE sch_sisauc.procedimento_trs SET internacao = TRUE
WHERE valor_enfermaria IS NOT NULL AND valor_apartamento IS NOT NULL;