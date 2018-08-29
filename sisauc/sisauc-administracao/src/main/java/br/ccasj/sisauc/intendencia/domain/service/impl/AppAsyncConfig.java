package br.ccasj.sisauc.intendencia.domain.service.impl;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
@ComponentScan("br.ccasj.sisauc.intendencia.domain.service.impl")
public class AppAsyncConfig {

}
