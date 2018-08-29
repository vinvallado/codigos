--
-- PostgreSQL database dump
--

-- Dumped from database version 9.1.13
-- Dumped by pg_dump version 9.1.14
-- Started on 2014-10-30 09:16:33 BRST

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 7 (class 2615 OID 34632)
-- Name: sch_sisauc; Type: SCHEMA; Schema: -; Owner: sisauc
--

CREATE SCHEMA sch_sisauc;


ALTER SCHEMA sch_sisauc OWNER TO sisauc;

SET search_path = sch_sisauc, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 194 (class 1259 OID 34834)
-- Dependencies: 7
-- Name: arquivo; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE arquivo (
    id integer NOT NULL,
    nome character varying(255) NOT NULL,
    tamanho integer NOT NULL,
    data_insercao timestamp without time zone NOT NULL
);


ALTER TABLE sch_sisauc.arquivo OWNER TO sisauc;

--
-- TOC entry 193 (class 1259 OID 34832)
-- Dependencies: 7 194
-- Name: arquivo_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE arquivo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.arquivo_id_seq OWNER TO sisauc;

--
-- TOC entry 2464 (class 0 OID 0)
-- Dependencies: 193
-- Name: arquivo_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE arquivo_id_seq OWNED BY arquivo.id;


--
-- TOC entry 227 (class 1259 OID 35195)
-- Dependencies: 7
-- Name: auditoria_prospectiva; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE auditoria_prospectiva (
    id integer NOT NULL,
    observacoes text,
    data_final_auditoria timestamp without time zone NOT NULL,
    id_usuario_auditor integer NOT NULL,
    estado character varying(255) NOT NULL,
    id_solicitacao_procedimento integer NOT NULL
);


ALTER TABLE sch_sisauc.auditoria_prospectiva OWNER TO sisauc;

--
-- TOC entry 226 (class 1259 OID 35193)
-- Dependencies: 227 7
-- Name: auditoria_prospectiva_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE auditoria_prospectiva_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.auditoria_prospectiva_id_seq OWNER TO sisauc;

--
-- TOC entry 2465 (class 0 OID 0)
-- Dependencies: 226
-- Name: auditoria_prospectiva_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE auditoria_prospectiva_id_seq OWNED BY auditoria_prospectiva.id;


--
-- TOC entry 230 (class 1259 OID 35241)
-- Dependencies: 7
-- Name: auditoria_resultado_procedimento_auditoria; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE auditoria_resultado_procedimento_auditoria (
    id_auditoria_prospectiva integer NOT NULL,
    id_resposta_procedimento_auditoria integer NOT NULL
);


ALTER TABLE sch_sisauc.auditoria_resultado_procedimento_auditoria OWNER TO sisauc;

--
-- TOC entry 246 (class 1259 OID 35403)
-- Dependencies: 7
-- Name: auditoria_retrospectiva; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE auditoria_retrospectiva (
    id integer NOT NULL,
    valor_apresentado double precision,
    valor_auditado double precision,
    valor_final double precision,
    procedimento_realizado boolean NOT NULL,
    justificativa_valor_final text,
    justificativa_valor_auditado text
);


ALTER TABLE sch_sisauc.auditoria_retrospectiva OWNER TO sisauc;

--
-- TOC entry 245 (class 1259 OID 35401)
-- Dependencies: 246 7
-- Name: auditoria_retrospectiva_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE auditoria_retrospectiva_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.auditoria_retrospectiva_id_seq OWNER TO sisauc;

--
-- TOC entry 2466 (class 0 OID 0)
-- Dependencies: 245
-- Name: auditoria_retrospectiva_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE auditoria_retrospectiva_id_seq OWNED BY auditoria_retrospectiva.id;


--
-- TOC entry 258 (class 1259 OID 35498)
-- Dependencies: 7
-- Name: auditoria_retrospectiva_metadado_valor_auditoria_retrospectiva; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE auditoria_retrospectiva_metadado_valor_auditoria_retrospectiva (
    id_auditoria_retrospectiva integer NOT NULL,
    id_metadado_valor_auditoria_retrospectiva integer NOT NULL
);


ALTER TABLE sch_sisauc.auditoria_retrospectiva_metadado_valor_auditoria_retrospectiva OWNER TO sisauc;

--
-- TOC entry 201 (class 1259 OID 34861)
-- Dependencies: 2102 2103 7
-- Name: beneficiario; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE beneficiario (
    id bigint NOT NULL,
    nome character varying(200),
    saram character varying(15),
    cpf character(11),
    sexo character(1) DEFAULT 'M'::bpchar,
    id_beneficiario_titular integer,
    id_organizacao_militar integer,
    id_posto_graduacao integer,
    id_convenio integer,
    tipo_contribuicao character varying(20),
    data_nascimento timestamp with time zone,
    ativo boolean DEFAULT false NOT NULL
);


ALTER TABLE sch_sisauc.beneficiario OWNER TO sisauc;

--
-- TOC entry 192 (class 1259 OID 34826)
-- Dependencies: 7
-- Name: cid; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE cid (
    id integer NOT NULL,
    codigo character varying(20) NOT NULL,
    descricao character varying(254) NOT NULL,
    id_grupo_cid integer
);


ALTER TABLE sch_sisauc.cid OWNER TO sisauc;

--
-- TOC entry 191 (class 1259 OID 34824)
-- Dependencies: 192 7
-- Name: cid_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE cid_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.cid_id_seq OWNER TO sisauc;

--
-- TOC entry 2467 (class 0 OID 0)
-- Dependencies: 191
-- Name: cid_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE cid_id_seq OWNED BY cid.id;


--
-- TOC entry 180 (class 1259 OID 34704)
-- Dependencies: 7
-- Name: cidade; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE cidade (
    id integer NOT NULL,
    nome character varying(64) NOT NULL,
    id_estado integer NOT NULL
);


ALTER TABLE sch_sisauc.cidade OWNER TO sisauc;

--
-- TOC entry 179 (class 1259 OID 34702)
-- Dependencies: 7 180
-- Name: cidade_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE cidade_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.cidade_id_seq OWNER TO sisauc;

--
-- TOC entry 2468 (class 0 OID 0)
-- Dependencies: 179
-- Name: cidade_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE cidade_id_seq OWNED BY cidade.id;


--
-- TOC entry 211 (class 1259 OID 34911)
-- Dependencies: 7
-- Name: configuracao_edital_credenciado; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE configuracao_edital_credenciado (
    id integer NOT NULL,
    ativo boolean,
    data_insercao timestamp without time zone NOT NULL,
    data_exclusao timestamp without time zone,
    indice_reajuste_porte double precision,
    indice_reajuste_porte_anestesico double precision,
    indice_reajuste_custo_operacional double precision,
    id_edital_credenciamento integer,
    id_credenciado integer,
    indice_reajuste_auxiliares double precision
);


ALTER TABLE sch_sisauc.configuracao_edital_credenciado OWNER TO sisauc;

--
-- TOC entry 210 (class 1259 OID 34909)
-- Dependencies: 7 211
-- Name: configuracao_edital_credenciado_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE configuracao_edital_credenciado_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.configuracao_edital_credenciado_id_seq OWNER TO sisauc;

--
-- TOC entry 2469 (class 0 OID 0)
-- Dependencies: 210
-- Name: configuracao_edital_credenciado_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE configuracao_edital_credenciado_id_seq OWNED BY configuracao_edital_credenciado.id;


--
-- TOC entry 213 (class 1259 OID 34917)
-- Dependencies: 7
-- Name: configuracao_edital_credenciado_procedimento; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE configuracao_edital_credenciado_procedimento (
    id integer NOT NULL,
    ativo boolean,
    data_insercao timestamp without time zone NOT NULL,
    data_exclusao timestamp without time zone,
    valor double precision,
    id_tipo_cobranca character varying(20),
    id_configuracao_edital_credenciado integer,
    id_procedimento integer,
    id_especialidade integer
);


ALTER TABLE sch_sisauc.configuracao_edital_credenciado_procedimento OWNER TO sisauc;

--
-- TOC entry 212 (class 1259 OID 34915)
-- Dependencies: 7 213
-- Name: configuracao_edital_credenciado_procedimento_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE configuracao_edital_credenciado_procedimento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.configuracao_edital_credenciado_procedimento_id_seq OWNER TO sisauc;

--
-- TOC entry 2470 (class 0 OID 0)
-- Dependencies: 212
-- Name: configuracao_edital_credenciado_procedimento_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE configuracao_edital_credenciado_procedimento_id_seq OWNED BY configuracao_edital_credenciado_procedimento.id;


--
-- TOC entry 250 (class 1259 OID 35440)
-- Dependencies: 7
-- Name: configuracao_sistema; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE configuracao_sistema (
    id integer NOT NULL,
    bloquear_emissao_gab boolean
);


ALTER TABLE sch_sisauc.configuracao_sistema OWNER TO sisauc;

--
-- TOC entry 249 (class 1259 OID 35438)
-- Dependencies: 250 7
-- Name: configuracao_sistema_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE configuracao_sistema_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.configuracao_sistema_id_seq OWNER TO sisauc;

--
-- TOC entry 2471 (class 0 OID 0)
-- Dependencies: 249
-- Name: configuracao_sistema_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE configuracao_sistema_id_seq OWNED BY configuracao_sistema.id;


--
-- TOC entry 222 (class 1259 OID 35139)
-- Dependencies: 7
-- Name: configuracao_ws_sigameh; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE configuracao_ws_sigameh (
    id integer NOT NULL,
    url_wsdl character varying(500),
    url_servico character varying(200),
    nome_servico character varying(100),
    usuario character varying(20),
    senha character varying(20)
);


ALTER TABLE sch_sisauc.configuracao_ws_sigameh OWNER TO sisauc;

--
-- TOC entry 221 (class 1259 OID 35137)
-- Dependencies: 222 7
-- Name: configuracao_ws_sigameh_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE configuracao_ws_sigameh_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.configuracao_ws_sigameh_id_seq OWNER TO sisauc;

--
-- TOC entry 2472 (class 0 OID 0)
-- Dependencies: 221
-- Name: configuracao_ws_sigameh_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE configuracao_ws_sigameh_id_seq OWNED BY configuracao_ws_sigameh.id;


--
-- TOC entry 200 (class 1259 OID 34857)
-- Dependencies: 7
-- Name: convenio; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE convenio (
    id integer NOT NULL,
    descricao character varying(200),
    sigla character(5) NOT NULL
);


ALTER TABLE sch_sisauc.convenio OWNER TO sisauc;

--
-- TOC entry 199 (class 1259 OID 34855)
-- Dependencies: 200 7
-- Name: convenio_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE convenio_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.convenio_id_seq OWNER TO sisauc;

--
-- TOC entry 2473 (class 0 OID 0)
-- Dependencies: 199
-- Name: convenio_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE convenio_id_seq OWNED BY convenio.id;


--
-- TOC entry 169 (class 1259 OID 34653)
-- Dependencies: 2079 7
-- Name: credenciado; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE credenciado (
    id integer NOT NULL,
    cpf character varying(40),
    cnpj character varying(40),
    email character varying(120),
    id_tipo_pessoa character varying(40) NOT NULL,
    telefone1 character varying(40) NOT NULL,
    telefone2 character varying(40),
    razao_social character varying(256) NOT NULL,
    nome_fantasia character varying(256) NOT NULL,
    logradouro character varying(256) NOT NULL,
    complemento character varying(256) NOT NULL,
    cep character varying(10) NOT NULL,
    bairro character varying(256) NOT NULL,
    ativo boolean DEFAULT true,
    id_cidade integer NOT NULL
);


ALTER TABLE sch_sisauc.credenciado OWNER TO sisauc;

--
-- TOC entry 168 (class 1259 OID 34651)
-- Dependencies: 7 169
-- Name: credenciado_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE credenciado_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.credenciado_id_seq OWNER TO sisauc;

--
-- TOC entry 2474 (class 0 OID 0)
-- Dependencies: 168
-- Name: credenciado_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE credenciado_id_seq OWNED BY credenciado.id;


--
-- TOC entry 260 (class 1259 OID 35553)
-- Dependencies: 7
-- Name: desconto_beneficiario; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE desconto_beneficiario (
    id integer NOT NULL,
    numero character varying(255) NOT NULL,
    valor_total double precision,
    data_emissao timestamp without time zone NOT NULL,
    id_beneficiario integer NOT NULL
);


ALTER TABLE sch_sisauc.desconto_beneficiario OWNER TO sisauc;

--
-- TOC entry 259 (class 1259 OID 35551)
-- Dependencies: 7 260
-- Name: desconto_beneficiario_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE desconto_beneficiario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.desconto_beneficiario_id_seq OWNER TO sisauc;

--
-- TOC entry 2475 (class 0 OID 0)
-- Dependencies: 259
-- Name: desconto_beneficiario_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE desconto_beneficiario_id_seq OWNED BY desconto_beneficiario.id;


--
-- TOC entry 261 (class 1259 OID 35564)
-- Dependencies: 7
-- Name: desconto_beneficiario_itens; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE desconto_beneficiario_itens (
    id_desconto_beneficiario integer NOT NULL,
    id_item_gab integer NOT NULL
);


ALTER TABLE sch_sisauc.desconto_beneficiario_itens OWNER TO sisauc;

--
-- TOC entry 203 (class 1259 OID 34868)
-- Dependencies: 2105 7
-- Name: edital_credenciamento; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE edital_credenciamento (
    id integer NOT NULL,
    numero character varying(50) NOT NULL,
    descricao character varying(100),
    ativo boolean DEFAULT true,
    inicio date NOT NULL,
    fim date NOT NULL
);


ALTER TABLE sch_sisauc.edital_credenciamento OWNER TO sisauc;

--
-- TOC entry 202 (class 1259 OID 34866)
-- Dependencies: 7 203
-- Name: edital_credenciamento_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE edital_credenciamento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.edital_credenciamento_id_seq OWNER TO sisauc;

--
-- TOC entry 2476 (class 0 OID 0)
-- Dependencies: 202
-- Name: edital_credenciamento_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE edital_credenciamento_id_seq OWNED BY edital_credenciamento.id;


--
-- TOC entry 232 (class 1259 OID 35258)
-- Dependencies: 2121 7
-- Name: empenho; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE empenho (
    id integer NOT NULL,
    valor double precision NOT NULL,
    verba character varying(50),
    id_credenciado integer NOT NULL,
    data_criacao timestamp without time zone NOT NULL,
    data_limite timestamp without time zone NOT NULL,
    finalizado boolean DEFAULT false NOT NULL,
    numero character varying(50) NOT NULL
);


ALTER TABLE sch_sisauc.empenho OWNER TO sisauc;

--
-- TOC entry 231 (class 1259 OID 35256)
-- Dependencies: 232 7
-- Name: empenho_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE empenho_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.empenho_id_seq OWNER TO sisauc;

--
-- TOC entry 2477 (class 0 OID 0)
-- Dependencies: 231
-- Name: empenho_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE empenho_id_seq OWNED BY empenho.id;


--
-- TOC entry 173 (class 1259 OID 34674)
-- Dependencies: 2083 7
-- Name: especialidade; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE especialidade (
    id integer NOT NULL,
    sigla character varying(16) NOT NULL,
    nome character varying(256) NOT NULL,
    ativo boolean DEFAULT true
);


ALTER TABLE sch_sisauc.especialidade OWNER TO sisauc;

--
-- TOC entry 172 (class 1259 OID 34672)
-- Dependencies: 173 7
-- Name: especialidade_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE especialidade_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.especialidade_id_seq OWNER TO sisauc;

--
-- TOC entry 2478 (class 0 OID 0)
-- Dependencies: 172
-- Name: especialidade_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE especialidade_id_seq OWNED BY especialidade.id;


--
-- TOC entry 178 (class 1259 OID 34694)
-- Dependencies: 7
-- Name: estado; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE estado (
    id integer NOT NULL,
    nome character varying(64) NOT NULL,
    sigla character varying(2) NOT NULL,
    id_pais integer NOT NULL
);


ALTER TABLE sch_sisauc.estado OWNER TO sisauc;

--
-- TOC entry 177 (class 1259 OID 34692)
-- Dependencies: 7 178
-- Name: estado_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE estado_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.estado_id_seq OWNER TO sisauc;

--
-- TOC entry 2479 (class 0 OID 0)
-- Dependencies: 177
-- Name: estado_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE estado_id_seq OWNED BY estado.id;


--
-- TOC entry 238 (class 1259 OID 35314)
-- Dependencies: 7
-- Name: gab; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE gab (
    id integer NOT NULL,
    codigo character varying(255) NOT NULL,
    data_emissao timestamp without time zone NOT NULL,
    estado character varying(255) NOT NULL,
    id_auditoria_prospectiva integer,
    id_beneficiario integer NOT NULL,
    id_credenciado integer NOT NULL,
    id_organizacao_militar integer NOT NULL,
    justificativa_cancelamento_gab character varying(255),
    data_impressao timestamp without time zone
);


ALTER TABLE sch_sisauc.gab OWNER TO sisauc;

--
-- TOC entry 237 (class 1259 OID 35312)
-- Dependencies: 238 7
-- Name: gab_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE gab_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.gab_id_seq OWNER TO sisauc;

--
-- TOC entry 2480 (class 0 OID 0)
-- Dependencies: 237
-- Name: gab_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE gab_id_seq OWNED BY gab.id;


--
-- TOC entry 244 (class 1259 OID 35384)
-- Dependencies: 7
-- Name: gab_itens; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE gab_itens (
    id_gab integer NOT NULL,
    id_item_gab integer NOT NULL
);


ALTER TABLE sch_sisauc.gab_itens OWNER TO sisauc;

--
-- TOC entry 205 (class 1259 OID 34877)
-- Dependencies: 7
-- Name: grupo; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE grupo (
    id integer NOT NULL,
    codigo integer NOT NULL,
    descricao character varying(512) NOT NULL
);


ALTER TABLE sch_sisauc.grupo OWNER TO sisauc;

--
-- TOC entry 204 (class 1259 OID 34875)
-- Dependencies: 205 7
-- Name: grupo_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE grupo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.grupo_id_seq OWNER TO sisauc;

--
-- TOC entry 2481 (class 0 OID 0)
-- Dependencies: 204
-- Name: grupo_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE grupo_id_seq OWNED BY grupo.id;


--
-- TOC entry 248 (class 1259 OID 35414)
-- Dependencies: 7
-- Name: historico_auditoria_retrospectiva; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE historico_auditoria_retrospectiva (
    id integer NOT NULL,
    valor_apresentado double precision,
    valor_auditado double precision,
    valor_final double precision,
    procedimento_realizado boolean NOT NULL,
    justificativa_valor_final text,
    justificativa_valor_auditado text,
    id_auditoria_retrospectiva integer NOT NULL,
    data_alteracao timestamp without time zone NOT NULL,
    id_usuario integer NOT NULL
);


ALTER TABLE sch_sisauc.historico_auditoria_retrospectiva OWNER TO sisauc;

--
-- TOC entry 247 (class 1259 OID 35412)
-- Dependencies: 248 7
-- Name: historico_auditoria_retrospectiva_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE historico_auditoria_retrospectiva_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.historico_auditoria_retrospectiva_id_seq OWNER TO sisauc;

--
-- TOC entry 2482 (class 0 OID 0)
-- Dependencies: 247
-- Name: historico_auditoria_retrospectiva_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE historico_auditoria_retrospectiva_id_seq OWNED BY historico_auditoria_retrospectiva.id;


--
-- TOC entry 234 (class 1259 OID 35272)
-- Dependencies: 2123 7
-- Name: historico_empenho; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE historico_empenho (
    id integer NOT NULL,
    valor double precision NOT NULL,
    id_credenciado integer NOT NULL,
    data_criacao timestamp without time zone NOT NULL,
    data_limite timestamp without time zone NOT NULL,
    data_alteracao timestamp without time zone NOT NULL,
    finalizado boolean DEFAULT false NOT NULL,
    numero_empenho character varying(50) NOT NULL,
    id_usuario integer NOT NULL,
    id_empenho integer NOT NULL,
    verba character varying(50)
);


ALTER TABLE sch_sisauc.historico_empenho OWNER TO sisauc;

--
-- TOC entry 233 (class 1259 OID 35270)
-- Dependencies: 7 234
-- Name: historico_empenho_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE historico_empenho_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.historico_empenho_id_seq OWNER TO sisauc;

--
-- TOC entry 2483 (class 0 OID 0)
-- Dependencies: 233
-- Name: historico_empenho_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE historico_empenho_id_seq OWNED BY historico_empenho.id;


--
-- TOC entry 216 (class 1259 OID 35080)
-- Dependencies: 7
-- Name: historico_valor_configuracao_edital_credenciado; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE historico_valor_configuracao_edital_credenciado (
    id integer NOT NULL,
    indice_reajuste_porte double precision NOT NULL,
    indice_reajuste_porte_anestesico double precision NOT NULL,
    indice_reajuste_custo_operacional double precision NOT NULL,
    id_configuracao_edital_credenciamento integer NOT NULL,
    id_credenciado integer NOT NULL,
    data_alteracao timestamp without time zone NOT NULL,
    id_usuario integer
);


ALTER TABLE sch_sisauc.historico_valor_configuracao_edital_credenciado OWNER TO sisauc;

--
-- TOC entry 215 (class 1259 OID 35078)
-- Dependencies: 7 216
-- Name: historico_valor_configuracao_edital_credenciado_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE historico_valor_configuracao_edital_credenciado_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.historico_valor_configuracao_edital_credenciado_id_seq OWNER TO sisauc;

--
-- TOC entry 2484 (class 0 OID 0)
-- Dependencies: 215
-- Name: historico_valor_configuracao_edital_credenciado_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE historico_valor_configuracao_edital_credenciado_id_seq OWNED BY historico_valor_configuracao_edital_credenciado.id;


--
-- TOC entry 218 (class 1259 OID 35086)
-- Dependencies: 7
-- Name: historico_valor_configuracao_edital_procedimento; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE historico_valor_configuracao_edital_procedimento (
    id integer NOT NULL,
    valor double precision NOT NULL,
    id_tipo_cobranca character varying(10) NOT NULL,
    id_procedimento integer NOT NULL,
    id_especialidade integer,
    id_configuracao_edital_credenciado_procedimento integer NOT NULL,
    data_alteracao timestamp without time zone NOT NULL,
    id_usuario integer NOT NULL
);


ALTER TABLE sch_sisauc.historico_valor_configuracao_edital_procedimento OWNER TO sisauc;

--
-- TOC entry 217 (class 1259 OID 35084)
-- Dependencies: 7 218
-- Name: historico_valor_configuracao_edital_procedimento_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE historico_valor_configuracao_edital_procedimento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.historico_valor_configuracao_edital_procedimento_id_seq OWNER TO sisauc;

--
-- TOC entry 2485 (class 0 OID 0)
-- Dependencies: 217
-- Name: historico_valor_configuracao_edital_procedimento_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE historico_valor_configuracao_edital_procedimento_id_seq OWNED BY historico_valor_configuracao_edital_procedimento.id;


--
-- TOC entry 188 (class 1259 OID 34814)
-- Dependencies: 7
-- Name: historico_valor_porte; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE historico_valor_porte (
    id integer NOT NULL,
    valor double precision NOT NULL,
    id_porte integer NOT NULL,
    id_usuario integer NOT NULL,
    data_alteracao timestamp without time zone NOT NULL
);


ALTER TABLE sch_sisauc.historico_valor_porte OWNER TO sisauc;

--
-- TOC entry 190 (class 1259 OID 34820)
-- Dependencies: 7
-- Name: historico_valor_porte_anestesico; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE historico_valor_porte_anestesico (
    id integer NOT NULL,
    valor double precision NOT NULL,
    id_porte_anestesico integer NOT NULL,
    id_usuario integer NOT NULL,
    data_alteracao timestamp without time zone NOT NULL
);


ALTER TABLE sch_sisauc.historico_valor_porte_anestesico OWNER TO sisauc;

--
-- TOC entry 189 (class 1259 OID 34818)
-- Dependencies: 7 190
-- Name: historico_valor_porte_anestesico_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE historico_valor_porte_anestesico_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.historico_valor_porte_anestesico_id_seq OWNER TO sisauc;

--
-- TOC entry 2486 (class 0 OID 0)
-- Dependencies: 189
-- Name: historico_valor_porte_anestesico_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE historico_valor_porte_anestesico_id_seq OWNED BY historico_valor_porte_anestesico.id;


--
-- TOC entry 187 (class 1259 OID 34812)
-- Dependencies: 188 7
-- Name: historico_valor_porte_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE historico_valor_porte_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.historico_valor_porte_id_seq OWNER TO sisauc;

--
-- TOC entry 2487 (class 0 OID 0)
-- Dependencies: 187
-- Name: historico_valor_porte_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE historico_valor_porte_id_seq OWNED BY historico_valor_porte.id;


--
-- TOC entry 236 (class 1259 OID 35296)
-- Dependencies: 7
-- Name: historico_valor_unidade_multiplicadora; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE historico_valor_unidade_multiplicadora (
    id integer NOT NULL,
    id_unidade_multiplicadora integer NOT NULL,
    id_usuario integer NOT NULL,
    valor_filme double precision NOT NULL,
    valor_uco double precision NOT NULL,
    data_alteracao timestamp without time zone NOT NULL
);


ALTER TABLE sch_sisauc.historico_valor_unidade_multiplicadora OWNER TO sisauc;

--
-- TOC entry 235 (class 1259 OID 35294)
-- Dependencies: 7 236
-- Name: historico_valor_unidade_multiplicadora_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE historico_valor_unidade_multiplicadora_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.historico_valor_unidade_multiplicadora_id_seq OWNER TO sisauc;

--
-- TOC entry 2488 (class 0 OID 0)
-- Dependencies: 235
-- Name: historico_valor_unidade_multiplicadora_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE historico_valor_unidade_multiplicadora_id_seq OWNED BY historico_valor_unidade_multiplicadora.id;


--
-- TOC entry 240 (class 1259 OID 35345)
-- Dependencies: 7
-- Name: item_gab; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE item_gab (
    id integer NOT NULL,
    codigo character varying(255) NOT NULL,
    valor_total double precision NOT NULL,
    id_configuracao_edital_credenciado_procedimento integer NOT NULL,
    estado character varying(20) NOT NULL,
    id_auditoria_retrospectiva integer,
    descricao_opme character varying(255),
    valor_opme double precision
);


ALTER TABLE sch_sisauc.item_gab OWNER TO sisauc;

--
-- TOC entry 239 (class 1259 OID 35343)
-- Dependencies: 240 7
-- Name: item_gab_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE item_gab_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.item_gab_id_seq OWNER TO sisauc;

--
-- TOC entry 2489 (class 0 OID 0)
-- Dependencies: 239
-- Name: item_gab_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE item_gab_id_seq OWNED BY item_gab.id;


--
-- TOC entry 243 (class 1259 OID 35367)
-- Dependencies: 7
-- Name: item_gab_metadado_valor_item_gab; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE item_gab_metadado_valor_item_gab (
    id_item_gab integer NOT NULL,
    id_metadado_valor_item_gab integer NOT NULL
);


ALTER TABLE sch_sisauc.item_gab_metadado_valor_item_gab OWNER TO sisauc;

--
-- TOC entry 252 (class 1259 OID 35448)
-- Dependencies: 7
-- Name: lote; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE lote (
    id integer NOT NULL,
    numero character varying(255) NOT NULL,
    valor_total double precision,
    id_credenciado integer NOT NULL,
    id_organizacao_militar integer NOT NULL,
    data_criacao timestamp without time zone NOT NULL,
    id_nota_fiscal integer,
    conformidade boolean
);


ALTER TABLE sch_sisauc.lote OWNER TO sisauc;

--
-- TOC entry 251 (class 1259 OID 35446)
-- Dependencies: 252 7
-- Name: lote_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE lote_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.lote_id_seq OWNER TO sisauc;

--
-- TOC entry 2490 (class 0 OID 0)
-- Dependencies: 251
-- Name: lote_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE lote_id_seq OWNED BY lote.id;


--
-- TOC entry 253 (class 1259 OID 35459)
-- Dependencies: 7
-- Name: lote_itens; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE lote_itens (
    id_lote integer NOT NULL,
    id_item_gab integer NOT NULL
);


ALTER TABLE sch_sisauc.lote_itens OWNER TO sisauc;

--
-- TOC entry 253 (class 1259 OID 35459)
-- Dependencies: 7
-- Name: lote_ressarcimento_itens; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE lote_ressarcimento_itens (
    id_lote_ar integer NOT NULL,
    id_item_ar integer NOT NULL
);


ALTER TABLE sch_sisauc.lote_ressarcimento_itens OWNER TO sisauc;

--
-- TOC entry 171 (class 1259 OID 34667)
-- Dependencies: 2081 7
-- Name: medico; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE medico (
    id integer NOT NULL,
    nome character varying(256) NOT NULL,
    numero_conselho_regional character varying(50),
    tipo_profissional_saude character varying(50),
    ativo boolean DEFAULT true,
    militar boolean DEFAULT true,
    profissional_saude boolean DEFAULT true,
    id_organizacao_militar integer
);


ALTER TABLE sch_sisauc.medico OWNER TO sisauc;

--
-- TOC entry 174 (class 1259 OID 34679)
-- Dependencies: 7
-- Name: medico_especialidade; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE medico_especialidade (
    id_medico integer NOT NULL,
    id_especialidade integer NOT NULL
);


ALTER TABLE sch_sisauc.medico_especialidade OWNER TO sisauc;

--
-- TOC entry 170 (class 1259 OID 34665)
-- Dependencies: 7 171
-- Name: medico_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE medico_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.medico_id_seq OWNER TO sisauc;

--
-- TOC entry 2491 (class 0 OID 0)
-- Dependencies: 170
-- Name: medico_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE medico_id_seq OWNED BY medico.id;


--
-- TOC entry 257 (class 1259 OID 35489)
-- Dependencies: 7
-- Name: metadado_valor_auditoria_retrospectiva; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE metadado_valor_auditoria_retrospectiva (
    id integer NOT NULL,
    valor double precision NOT NULL,
    chave character varying(255) NOT NULL,
    descricao character varying(255) NOT NULL
);


ALTER TABLE sch_sisauc.metadado_valor_auditoria_retrospectiva OWNER TO sisauc;

--
-- TOC entry 256 (class 1259 OID 35487)
-- Dependencies: 257 7
-- Name: metadado_valor_auditoria_retrospectiva_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE metadado_valor_auditoria_retrospectiva_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.metadado_valor_auditoria_retrospectiva_id_seq OWNER TO sisauc;

--
-- TOC entry 2492 (class 0 OID 0)
-- Dependencies: 256
-- Name: metadado_valor_auditoria_retrospectiva_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE metadado_valor_auditoria_retrospectiva_id_seq OWNED BY metadado_valor_auditoria_retrospectiva.id;


--
-- TOC entry 242 (class 1259 OID 35358)
-- Dependencies: 7
-- Name: metadado_valor_item_gab; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE metadado_valor_item_gab (
    id integer NOT NULL,
    valor double precision NOT NULL,
    chave character varying(255) NOT NULL,
    descricao character varying(255) NOT NULL
);


ALTER TABLE sch_sisauc.metadado_valor_item_gab OWNER TO sisauc;

--
-- TOC entry 241 (class 1259 OID 35356)
-- Dependencies: 242 7
-- Name: metadado_valor_item_gab_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE metadado_valor_item_gab_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.metadado_valor_item_gab_id_seq OWNER TO sisauc;

--
-- TOC entry 2493 (class 0 OID 0)
-- Dependencies: 241
-- Name: metadado_valor_item_gab_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE metadado_valor_item_gab_id_seq OWNED BY metadado_valor_item_gab.id;


--
-- TOC entry 255 (class 1259 OID 35476)
-- Dependencies: 7
-- Name: nota_fiscal; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE nota_fiscal (
    id integer NOT NULL,
    numero character varying(255),
    data_apresentacao timestamp without time zone
);


ALTER TABLE sch_sisauc.nota_fiscal OWNER TO sisauc;

--
-- TOC entry 254 (class 1259 OID 35474)
-- Dependencies: 7 255
-- Name: nota_fiscal_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE nota_fiscal_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.nota_fiscal_id_seq OWNER TO sisauc;

--
-- TOC entry 2494 (class 0 OID 0)
-- Dependencies: 254
-- Name: nota_fiscal_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE nota_fiscal_id_seq OWNED BY nota_fiscal.id;


--
-- TOC entry 182 (class 1259 OID 34710)
-- Dependencies: 2088 2089 7
-- Name: organizacao_militar; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE organizacao_militar (
    id integer NOT NULL,
    sigla character varying(45) NOT NULL,
    nome character varying(140) NOT NULL,
    ativo boolean DEFAULT true,
    id_cidade integer,
    tipo_om character varying(45) NOT NULL,
    id_regional integer,
    regional_sistema boolean DEFAULT false
);


ALTER TABLE sch_sisauc.organizacao_militar OWNER TO sisauc;

--
-- TOC entry 181 (class 1259 OID 34708)
-- Dependencies: 7 182
-- Name: organizacao_militar_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE organizacao_militar_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.organizacao_militar_id_seq OWNER TO sisauc;

--
-- TOC entry 2495 (class 0 OID 0)
-- Dependencies: 181
-- Name: organizacao_militar_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE organizacao_militar_id_seq OWNED BY organizacao_militar.id;


--
-- TOC entry 176 (class 1259 OID 34684)
-- Dependencies: 7
-- Name: pais; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE pais (
    id integer NOT NULL,
    nome character varying(64) NOT NULL,
    sigla character varying(2) NOT NULL
);


ALTER TABLE sch_sisauc.pais OWNER TO sisauc;

--
-- TOC entry 175 (class 1259 OID 34682)
-- Dependencies: 7 176
-- Name: pais_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE pais_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.pais_id_seq OWNER TO sisauc;

--
-- TOC entry 2496 (class 0 OID 0)
-- Dependencies: 175
-- Name: pais_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE pais_id_seq OWNED BY pais.id;


--
-- TOC entry 185 (class 1259 OID 34729)
-- Dependencies: 2092 7
-- Name: perfil; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE perfil (
    id character varying(50) NOT NULL,
    nome character varying(50) NOT NULL,
    ativo boolean DEFAULT true
);


ALTER TABLE sch_sisauc.perfil OWNER TO sisauc;

--
-- TOC entry 165 (class 1259 OID 34635)
-- Dependencies: 2075 7
-- Name: porte; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE porte (
    id integer NOT NULL,
    codigo character varying(3) NOT NULL,
    valor double precision NOT NULL,
    ativo boolean DEFAULT true
);


ALTER TABLE sch_sisauc.porte OWNER TO sisauc;

--
-- TOC entry 167 (class 1259 OID 34644)
-- Dependencies: 2077 7
-- Name: porte_anestesico; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE porte_anestesico (
    id integer NOT NULL,
    codigo character varying(3) NOT NULL,
    valor double precision NOT NULL,
    ativo boolean DEFAULT true
);


ALTER TABLE sch_sisauc.porte_anestesico OWNER TO sisauc;

--
-- TOC entry 166 (class 1259 OID 34642)
-- Dependencies: 7 167
-- Name: porte_anestesico_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE porte_anestesico_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.porte_anestesico_id_seq OWNER TO sisauc;

--
-- TOC entry 2497 (class 0 OID 0)
-- Dependencies: 166
-- Name: porte_anestesico_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE porte_anestesico_id_seq OWNED BY porte_anestesico.id;


--
-- TOC entry 164 (class 1259 OID 34633)
-- Dependencies: 165 7
-- Name: porte_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE porte_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.porte_id_seq OWNER TO sisauc;

--
-- TOC entry 2498 (class 0 OID 0)
-- Dependencies: 164
-- Name: porte_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE porte_id_seq OWNED BY porte.id;


--
-- TOC entry 198 (class 1259 OID 34851)
-- Dependencies: 7
-- Name: posto_graduacao; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE posto_graduacao (
    id integer NOT NULL,
    descricao character varying(100),
    sigla character(2) NOT NULL
);


ALTER TABLE sch_sisauc.posto_graduacao OWNER TO sisauc;

--
-- TOC entry 197 (class 1259 OID 34849)
-- Dependencies: 7 198
-- Name: posto_graduacao_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE posto_graduacao_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.posto_graduacao_id_seq OWNER TO sisauc;

--
-- TOC entry 2499 (class 0 OID 0)
-- Dependencies: 197
-- Name: posto_graduacao_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE posto_graduacao_id_seq OWNED BY posto_graduacao.id;


--
-- TOC entry 209 (class 1259 OID 34899)
-- Dependencies: 2109 7
-- Name: procedimento; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE procedimento (
    id integer NOT NULL,
    codigo character varying(10) NOT NULL,
    descricao character varying(512) NOT NULL,
    numero_auxiliares integer,
    quantidade_filme double precision,
    incidencias integer,
    custo_operacional double precision,
    id_porte integer,
    id_porte_anestesico integer,
    id_subgrupo integer NOT NULL,
    valor_oculto boolean DEFAULT false NOT NULL,
    deflator_porte double precision
);


ALTER TABLE sch_sisauc.procedimento OWNER TO sisauc;

--
-- TOC entry 208 (class 1259 OID 34897)
-- Dependencies: 209 7
-- Name: procedimento_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE procedimento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.procedimento_id_seq OWNER TO sisauc;

--
-- TOC entry 2500 (class 0 OID 0)
-- Dependencies: 208
-- Name: procedimento_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE procedimento_id_seq OWNED BY procedimento.id;


--
-- TOC entry 224 (class 1259 OID 35150)
-- Dependencies: 7
-- Name: procedimento_pedido_solicitacao; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE procedimento_pedido_solicitacao (
    id integer NOT NULL,
    id_configuracao_edital_credenciado_procedimento integer,
    quantidade integer
);


ALTER TABLE sch_sisauc.procedimento_pedido_solicitacao OWNER TO sisauc;

--
-- TOC entry 223 (class 1259 OID 35148)
-- Dependencies: 224 7
-- Name: procedimento_pedido_solicitacao_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE procedimento_pedido_solicitacao_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.procedimento_pedido_solicitacao_id_seq OWNER TO sisauc;

--
-- TOC entry 2501 (class 0 OID 0)
-- Dependencies: 223
-- Name: procedimento_pedido_solicitacao_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE procedimento_pedido_solicitacao_id_seq OWNED BY procedimento_pedido_solicitacao.id;


--
-- TOC entry 229 (class 1259 OID 35216)
-- Dependencies: 2119 7
-- Name: resposta_procedimento_auditoria; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE resposta_procedimento_auditoria (
    id integer NOT NULL,
    id_configuracao_edital_credenciado_procedimento integer,
    aprovado boolean DEFAULT false NOT NULL,
    justificativa text,
    opme boolean,
    opme_descricao character varying(255),
    opme_valor double precision
);


ALTER TABLE sch_sisauc.resposta_procedimento_auditoria OWNER TO sisauc;

--
-- TOC entry 228 (class 1259 OID 35214)
-- Dependencies: 7 229
-- Name: resposta_procedimento_auditoria_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE resposta_procedimento_auditoria_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.resposta_procedimento_auditoria_id_seq OWNER TO sisauc;

--
-- TOC entry 2502 (class 0 OID 0)
-- Dependencies: 228
-- Name: resposta_procedimento_auditoria_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE resposta_procedimento_auditoria_id_seq OWNED BY resposta_procedimento_auditoria.id;


--
-- TOC entry 196 (class 1259 OID 34842)
-- Dependencies: 2098 2099 7
-- Name: solicitacao; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE solicitacao (
    id integer NOT NULL,
    dados_clinicos character varying(1024) NOT NULL,
    finalidade character varying(255) NOT NULL,
    local_sugerido_exame character varying(255),
    inspecao_saude boolean,
    data_insercao_sistema timestamp without time zone,
    data_solicitacao_sistema timestamp without time zone,
    data_envio_auditoria timestamp without time zone,
    estado character varying(60),
    id_cid integer,
    id_beneficiario integer NOT NULL,
    id_organizacao_militar integer NOT NULL,
    id_usuario integer NOT NULL,
    id_medico integer NOT NULL,
    id_arquivo integer,
    data_ultima_alteracao_estado timestamp without time zone,
    urgente boolean DEFAULT false NOT NULL,
    internacao boolean DEFAULT false NOT NULL,
    local_internacao character varying(25),
    texto_inconsistencia text,
    numero character varying(25) NOT NULL
);


ALTER TABLE sch_sisauc.solicitacao OWNER TO sisauc;

--
-- TOC entry 195 (class 1259 OID 34840)
-- Dependencies: 7 196
-- Name: solicitacao_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE solicitacao_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.solicitacao_id_seq OWNER TO sisauc;

--
-- TOC entry 2503 (class 0 OID 0)
-- Dependencies: 195
-- Name: solicitacao_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE solicitacao_id_seq OWNED BY solicitacao.id;


--
-- TOC entry 225 (class 1259 OID 35171)
-- Dependencies: 7
-- Name: solicitacao_pedidos_procedimento; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE solicitacao_pedidos_procedimento (
    id_solicitacao integer NOT NULL,
    id_procedimento_pedido_solicitacao integer NOT NULL
);


ALTER TABLE sch_sisauc.solicitacao_pedidos_procedimento OWNER TO sisauc;

--
-- TOC entry 207 (class 1259 OID 34888)
-- Dependencies: 7
-- Name: subgrupo; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE subgrupo (
    id integer NOT NULL,
    codigo integer NOT NULL,
    descricao character varying(512) NOT NULL,
    id_grupo integer NOT NULL
);


ALTER TABLE sch_sisauc.subgrupo OWNER TO sisauc;

--
-- TOC entry 206 (class 1259 OID 34886)
-- Dependencies: 7 207
-- Name: subgrupo_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE subgrupo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.subgrupo_id_seq OWNER TO sisauc;

--
-- TOC entry 2504 (class 0 OID 0)
-- Dependencies: 206
-- Name: subgrupo_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE subgrupo_id_seq OWNED BY subgrupo.id;


--
-- TOC entry 214 (class 1259 OID 34921)
-- Dependencies: 7
-- Name: tipo_cobranca_credenciado_procedimento; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE tipo_cobranca_credenciado_procedimento (
    id character varying(20) NOT NULL
);


ALTER TABLE sch_sisauc.tipo_cobranca_credenciado_procedimento OWNER TO sisauc;

--
-- TOC entry 220 (class 1259 OID 35131)
-- Dependencies: 7
-- Name: unidade_multiplicadora; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE unidade_multiplicadora (
    id integer NOT NULL,
    valor_filme double precision NOT NULL,
    valor_uco double precision NOT NULL
);


ALTER TABLE sch_sisauc.unidade_multiplicadora OWNER TO sisauc;

--
-- TOC entry 219 (class 1259 OID 35129)
-- Dependencies: 7 220
-- Name: unidade_multiplicadora_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE unidade_multiplicadora_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.unidade_multiplicadora_id_seq OWNER TO sisauc;

--
-- TOC entry 2505 (class 0 OID 0)
-- Dependencies: 219
-- Name: unidade_multiplicadora_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE unidade_multiplicadora_id_seq OWNED BY unidade_multiplicadora.id;


--
-- TOC entry 184 (class 1259 OID 34719)
-- Dependencies: 2091 7
-- Name: usuario; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE usuario (
    id integer NOT NULL,
    nome character varying(255) NOT NULL,
    email character varying(255),
    ativo boolean DEFAULT true,
    login character varying(20) NOT NULL,
    senha character varying(64) NOT NULL,
    id_organizacao_militar integer NOT NULL
);


ALTER TABLE sch_sisauc.usuario OWNER TO sisauc;

--
-- TOC entry 183 (class 1259 OID 34717)
-- Dependencies: 184 7
-- Name: usuario_id_seq; Type: SEQUENCE; Schema: sch_sisauc; Owner: sisauc
--

CREATE SEQUENCE usuario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sch_sisauc.usuario_id_seq OWNER TO sisauc;

--
-- TOC entry 2506 (class 0 OID 0)
-- Dependencies: 183
-- Name: usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: sch_sisauc; Owner: sisauc
--

ALTER SEQUENCE usuario_id_seq OWNED BY usuario.id;


--
-- TOC entry 186 (class 1259 OID 34733)
-- Dependencies: 7
-- Name: usuario_perfil; Type: TABLE; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

CREATE TABLE usuario_perfil (
    id_usuario integer NOT NULL,
    id_perfil character varying(50) NOT NULL
);


ALTER TABLE sch_sisauc.usuario_perfil OWNER TO sisauc;

--
-- TOC entry 2096 (class 2604 OID 34837)
-- Dependencies: 193 194 194
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY arquivo ALTER COLUMN id SET DEFAULT nextval('arquivo_id_seq'::regclass);


--
-- TOC entry 2117 (class 2604 OID 35198)
-- Dependencies: 226 227 227
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY auditoria_prospectiva ALTER COLUMN id SET DEFAULT nextval('auditoria_prospectiva_id_seq'::regclass);


--
-- TOC entry 2128 (class 2604 OID 35406)
-- Dependencies: 245 246 246
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY auditoria_retrospectiva ALTER COLUMN id SET DEFAULT nextval('auditoria_retrospectiva_id_seq'::regclass);


--
-- TOC entry 2095 (class 2604 OID 34829)
-- Dependencies: 192 191 192
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY cid ALTER COLUMN id SET DEFAULT nextval('cid_id_seq'::regclass);


--
-- TOC entry 2086 (class 2604 OID 34707)
-- Dependencies: 179 180 180
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY cidade ALTER COLUMN id SET DEFAULT nextval('cidade_id_seq'::regclass);


--
-- TOC entry 2110 (class 2604 OID 34914)
-- Dependencies: 210 211 211
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY configuracao_edital_credenciado ALTER COLUMN id SET DEFAULT nextval('configuracao_edital_credenciado_id_seq'::regclass);


--
-- TOC entry 2111 (class 2604 OID 34920)
-- Dependencies: 212 213 213
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY configuracao_edital_credenciado_procedimento ALTER COLUMN id SET DEFAULT nextval('configuracao_edital_credenciado_procedimento_id_seq'::regclass);


--
-- TOC entry 2130 (class 2604 OID 35443)
-- Dependencies: 249 250 250
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY configuracao_sistema ALTER COLUMN id SET DEFAULT nextval('configuracao_sistema_id_seq'::regclass);


--
-- TOC entry 2115 (class 2604 OID 35142)
-- Dependencies: 221 222 222
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY configuracao_ws_sigameh ALTER COLUMN id SET DEFAULT nextval('configuracao_ws_sigameh_id_seq'::regclass);


--
-- TOC entry 2101 (class 2604 OID 34860)
-- Dependencies: 200 199 200
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY convenio ALTER COLUMN id SET DEFAULT nextval('convenio_id_seq'::regclass);


--
-- TOC entry 2078 (class 2604 OID 34656)
-- Dependencies: 168 169 169
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY credenciado ALTER COLUMN id SET DEFAULT nextval('credenciado_id_seq'::regclass);


--
-- TOC entry 2134 (class 2604 OID 35556)
-- Dependencies: 260 259 260
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY desconto_beneficiario ALTER COLUMN id SET DEFAULT nextval('desconto_beneficiario_id_seq'::regclass);


--
-- TOC entry 2104 (class 2604 OID 34871)
-- Dependencies: 203 202 203
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY edital_credenciamento ALTER COLUMN id SET DEFAULT nextval('edital_credenciamento_id_seq'::regclass);


--
-- TOC entry 2120 (class 2604 OID 35261)
-- Dependencies: 232 231 232
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY empenho ALTER COLUMN id SET DEFAULT nextval('empenho_id_seq'::regclass);


--
-- TOC entry 2082 (class 2604 OID 34677)
-- Dependencies: 173 172 173
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY especialidade ALTER COLUMN id SET DEFAULT nextval('especialidade_id_seq'::regclass);


--
-- TOC entry 2085 (class 2604 OID 34697)
-- Dependencies: 177 178 178
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY estado ALTER COLUMN id SET DEFAULT nextval('estado_id_seq'::regclass);


--
-- TOC entry 2125 (class 2604 OID 35317)
-- Dependencies: 237 238 238
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY gab ALTER COLUMN id SET DEFAULT nextval('gab_id_seq'::regclass);


--
-- TOC entry 2106 (class 2604 OID 34880)
-- Dependencies: 204 205 205
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY grupo ALTER COLUMN id SET DEFAULT nextval('grupo_id_seq'::regclass);


--
-- TOC entry 2129 (class 2604 OID 35417)
-- Dependencies: 248 247 248
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY historico_auditoria_retrospectiva ALTER COLUMN id SET DEFAULT nextval('historico_auditoria_retrospectiva_id_seq'::regclass);


--
-- TOC entry 2122 (class 2604 OID 35275)
-- Dependencies: 233 234 234
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY historico_empenho ALTER COLUMN id SET DEFAULT nextval('historico_empenho_id_seq'::regclass);


--
-- TOC entry 2112 (class 2604 OID 35083)
-- Dependencies: 215 216 216
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY historico_valor_configuracao_edital_credenciado ALTER COLUMN id SET DEFAULT nextval('historico_valor_configuracao_edital_credenciado_id_seq'::regclass);


--
-- TOC entry 2113 (class 2604 OID 35089)
-- Dependencies: 218 217 218
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY historico_valor_configuracao_edital_procedimento ALTER COLUMN id SET DEFAULT nextval('historico_valor_configuracao_edital_procedimento_id_seq'::regclass);


--
-- TOC entry 2093 (class 2604 OID 34817)
-- Dependencies: 187 188 188
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY historico_valor_porte ALTER COLUMN id SET DEFAULT nextval('historico_valor_porte_id_seq'::regclass);


--
-- TOC entry 2094 (class 2604 OID 34823)
-- Dependencies: 189 190 190
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY historico_valor_porte_anestesico ALTER COLUMN id SET DEFAULT nextval('historico_valor_porte_anestesico_id_seq'::regclass);


--
-- TOC entry 2124 (class 2604 OID 35299)
-- Dependencies: 236 235 236
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY historico_valor_unidade_multiplicadora ALTER COLUMN id SET DEFAULT nextval('historico_valor_unidade_multiplicadora_id_seq'::regclass);


--
-- TOC entry 2126 (class 2604 OID 35348)
-- Dependencies: 240 239 240
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY item_gab ALTER COLUMN id SET DEFAULT nextval('item_gab_id_seq'::regclass);


--
-- TOC entry 2131 (class 2604 OID 35451)
-- Dependencies: 252 251 252
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY lote ALTER COLUMN id SET DEFAULT nextval('lote_id_seq'::regclass);


--
-- TOC entry 2080 (class 2604 OID 34670)
-- Dependencies: 171 170 171
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY medico ALTER COLUMN id SET DEFAULT nextval('medico_id_seq'::regclass);


--
-- TOC entry 2133 (class 2604 OID 35492)
-- Dependencies: 257 256 257
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY metadado_valor_auditoria_retrospectiva ALTER COLUMN id SET DEFAULT nextval('metadado_valor_auditoria_retrospectiva_id_seq'::regclass);


--
-- TOC entry 2127 (class 2604 OID 35361)
-- Dependencies: 241 242 242
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY metadado_valor_item_gab ALTER COLUMN id SET DEFAULT nextval('metadado_valor_item_gab_id_seq'::regclass);


--
-- TOC entry 2132 (class 2604 OID 35479)
-- Dependencies: 255 254 255
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY nota_fiscal ALTER COLUMN id SET DEFAULT nextval('nota_fiscal_id_seq'::regclass);


--
-- TOC entry 2087 (class 2604 OID 34713)
-- Dependencies: 182 181 182
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY organizacao_militar ALTER COLUMN id SET DEFAULT nextval('organizacao_militar_id_seq'::regclass);


--
-- TOC entry 2084 (class 2604 OID 34687)
-- Dependencies: 176 175 176
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY pais ALTER COLUMN id SET DEFAULT nextval('pais_id_seq'::regclass);


--
-- TOC entry 2074 (class 2604 OID 34638)
-- Dependencies: 164 165 165
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY porte ALTER COLUMN id SET DEFAULT nextval('porte_id_seq'::regclass);


--
-- TOC entry 2076 (class 2604 OID 34647)
-- Dependencies: 166 167 167
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY porte_anestesico ALTER COLUMN id SET DEFAULT nextval('porte_anestesico_id_seq'::regclass);


--
-- TOC entry 2100 (class 2604 OID 34854)
-- Dependencies: 198 197 198
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY posto_graduacao ALTER COLUMN id SET DEFAULT nextval('posto_graduacao_id_seq'::regclass);


--
-- TOC entry 2108 (class 2604 OID 34902)
-- Dependencies: 208 209 209
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY procedimento ALTER COLUMN id SET DEFAULT nextval('procedimento_id_seq'::regclass);


--
-- TOC entry 2116 (class 2604 OID 35153)
-- Dependencies: 224 223 224
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY procedimento_pedido_solicitacao ALTER COLUMN id SET DEFAULT nextval('procedimento_pedido_solicitacao_id_seq'::regclass);


--
-- TOC entry 2118 (class 2604 OID 35219)
-- Dependencies: 229 228 229
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY resposta_procedimento_auditoria ALTER COLUMN id SET DEFAULT nextval('resposta_procedimento_auditoria_id_seq'::regclass);


--
-- TOC entry 2097 (class 2604 OID 34845)
-- Dependencies: 195 196 196
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY solicitacao ALTER COLUMN id SET DEFAULT nextval('solicitacao_id_seq'::regclass);


--
-- TOC entry 2107 (class 2604 OID 34891)
-- Dependencies: 206 207 207
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY subgrupo ALTER COLUMN id SET DEFAULT nextval('subgrupo_id_seq'::regclass);


--
-- TOC entry 2114 (class 2604 OID 35134)
-- Dependencies: 220 219 220
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY unidade_multiplicadora ALTER COLUMN id SET DEFAULT nextval('unidade_multiplicadora_id_seq'::regclass);


--
-- TOC entry 2090 (class 2604 OID 34722)
-- Dependencies: 184 183 184
-- Name: id; Type: DEFAULT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY usuario ALTER COLUMN id SET DEFAULT nextval('usuario_id_seq'::regclass);


--
-- TOC entry 2278 (class 2606 OID 35502)
-- Dependencies: 258 258 258 2461
-- Name: auditoria_retrospectiva_metadado_valor_auditoria_retrospec_pkey; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY auditoria_retrospectiva_metadado_valor_auditoria_retrospectiva
    ADD CONSTRAINT auditoria_retrospectiva_metadado_valor_auditoria_retrospec_pkey PRIMARY KEY (id_auditoria_retrospectiva, id_metadado_valor_auditoria_retrospectiva);


--
-- TOC entry 2264 (class 2606 OID 35411)
-- Dependencies: 246 246 2461
-- Name: auditoria_retrospectiva_pkey; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY auditoria_retrospectiva
    ADD CONSTRAINT auditoria_retrospectiva_pkey PRIMARY KEY (id);


--
-- TOC entry 2186 (class 2606 OID 34831)
-- Dependencies: 192 192 2461
-- Name: cid_codigo_key; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY cid
    ADD CONSTRAINT cid_codigo_key UNIQUE (codigo);


--
-- TOC entry 2268 (class 2606 OID 35445)
-- Dependencies: 250 250 2461
-- Name: configuracao_sistema_pkey; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY configuracao_sistema
    ADD CONSTRAINT configuracao_sistema_pkey PRIMARY KEY (id);


--
-- TOC entry 2232 (class 2606 OID 35147)
-- Dependencies: 222 222 2461
-- Name: configuracao_ws_sigameh_pkey; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY configuracao_ws_sigameh
    ADD CONSTRAINT configuracao_ws_sigameh_pkey PRIMARY KEY (id);


--
-- TOC entry 2198 (class 2606 OID 35027)
-- Dependencies: 200 200 2461
-- Name: convenio_sigla_key; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY convenio
    ADD CONSTRAINT convenio_sigla_key UNIQUE (sigla);


--
-- TOC entry 2144 (class 2606 OID 34664)
-- Dependencies: 169 169 2461
-- Name: credenciado_cnpj_key; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY credenciado
    ADD CONSTRAINT credenciado_cnpj_key UNIQUE (cnpj);


--
-- TOC entry 2146 (class 2606 OID 34662)
-- Dependencies: 169 169 2461
-- Name: credenciado_cpf_key; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY credenciado
    ADD CONSTRAINT credenciado_cpf_key UNIQUE (cpf);


--
-- TOC entry 2282 (class 2606 OID 35568)
-- Dependencies: 261 261 261 2461
-- Name: desconto_beneficiario_itens_pkey; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY desconto_beneficiario_itens
    ADD CONSTRAINT desconto_beneficiario_itens_pkey PRIMARY KEY (id_desconto_beneficiario, id_item_gab);


--
-- TOC entry 2280 (class 2606 OID 35558)
-- Dependencies: 260 260 2461
-- Name: desconto_beneficiario_pkey; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY desconto_beneficiario
    ADD CONSTRAINT desconto_beneficiario_pkey PRIMARY KEY (id);


--
-- TOC entry 2204 (class 2606 OID 35187)
-- Dependencies: 203 203 2461
-- Name: edital_credenciamento_numero_key; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY edital_credenciamento
    ADD CONSTRAINT edital_credenciamento_numero_key UNIQUE (numero);


--
-- TOC entry 2244 (class 2606 OID 35264)
-- Dependencies: 232 232 2461
-- Name: empenho_pkey; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY empenho
    ADD CONSTRAINT empenho_pkey PRIMARY KEY (id);


--
-- TOC entry 2162 (class 2606 OID 34699)
-- Dependencies: 178 178 2461
-- Name: estado_nome_key; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY estado
    ADD CONSTRAINT estado_nome_key UNIQUE (nome);


--
-- TOC entry 2164 (class 2606 OID 34701)
-- Dependencies: 178 178 2461
-- Name: estado_sigla_key; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY estado
    ADD CONSTRAINT estado_sigla_key UNIQUE (sigla);


--
-- TOC entry 2260 (class 2606 OID 35388)
-- Dependencies: 244 244 244 2461
-- Name: gab_itens_pkey; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY gab_itens
    ADD CONSTRAINT gab_itens_pkey PRIMARY KEY (id_gab, id_item_gab);


--
-- TOC entry 2250 (class 2606 OID 35322)
-- Dependencies: 238 238 2461
-- Name: gab_pkey; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY gab
    ADD CONSTRAINT gab_pkey PRIMARY KEY (id);


--
-- TOC entry 2208 (class 2606 OID 34885)
-- Dependencies: 205 205 2461
-- Name: grupo_codigo_key; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY grupo
    ADD CONSTRAINT grupo_codigo_key UNIQUE (codigo);


--
-- TOC entry 2266 (class 2606 OID 35422)
-- Dependencies: 248 248 2461
-- Name: historico_auditoria_retrospectiva_pkey; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY historico_auditoria_retrospectiva
    ADD CONSTRAINT historico_auditoria_retrospectiva_pkey PRIMARY KEY (id);


--
-- TOC entry 2246 (class 2606 OID 35278)
-- Dependencies: 234 234 2461
-- Name: historico_empenho_pkey; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY historico_empenho
    ADD CONSTRAINT historico_empenho_pkey PRIMARY KEY (id);


--
-- TOC entry 2256 (class 2606 OID 35371)
-- Dependencies: 243 243 243 2461
-- Name: item_gab_metadado_valor_item_gab_pkey; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY item_gab_metadado_valor_item_gab
    ADD CONSTRAINT item_gab_metadado_valor_item_gab_pkey PRIMARY KEY (id_item_gab, id_metadado_valor_item_gab);


--
-- TOC entry 2252 (class 2606 OID 35350)
-- Dependencies: 240 240 2461
-- Name: item_gab_pkey; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY item_gab
    ADD CONSTRAINT item_gab_pkey PRIMARY KEY (id);


--
-- TOC entry 2272 (class 2606 OID 35463)
-- Dependencies: 253 253 253 2461
-- Name: lote_itens_pkey; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY lote_itens
    ADD CONSTRAINT lote_itens_pkey PRIMARY KEY (id_lote, id_item_gab);

--
-- TOC entry ? (class ? OID ?)
-- Dependencies: ? ? ? ?
-- Name: lote_ressarcimento_itens_pkey; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY lote_ressarcimento_itens
    ADD CONSTRAINT lote_ressarcimento_itens_pkey PRIMARY KEY (id_lote_ar, id_item_ar);


--
-- TOC entry 2270 (class 2606 OID 35453)
-- Dependencies: 252 252 2461
-- Name: lote_pkey; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY lote
    ADD CONSTRAINT lote_pkey PRIMARY KEY (id);


--
-- TOC entry 2276 (class 2606 OID 35497)
-- Dependencies: 257 257 2461
-- Name: metadado_valor_auditoria_retrospectivapkey; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY metadado_valor_auditoria_retrospectiva
    ADD CONSTRAINT metadado_valor_auditoria_retrospectivapkey PRIMARY KEY (id);


--
-- TOC entry 2254 (class 2606 OID 35366)
-- Dependencies: 242 242 2461
-- Name: metadado_valor_item_gab_pkey; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY metadado_valor_item_gab
    ADD CONSTRAINT metadado_valor_item_gab_pkey PRIMARY KEY (id);


--
-- TOC entry 2274 (class 2606 OID 35481)
-- Dependencies: 255 255 2461
-- Name: nota_fiscal_pkey; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY nota_fiscal
    ADD CONSTRAINT nota_fiscal_pkey PRIMARY KEY (id);


--
-- TOC entry 2170 (class 2606 OID 34716)
-- Dependencies: 182 182 2461
-- Name: organizacao_militar_sigla_key; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY organizacao_militar
    ADD CONSTRAINT organizacao_militar_sigla_key UNIQUE (sigla);


--
-- TOC entry 2156 (class 2606 OID 34689)
-- Dependencies: 176 176 2461
-- Name: pais_nome_key; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY pais
    ADD CONSTRAINT pais_nome_key UNIQUE (nome);


--
-- TOC entry 2158 (class 2606 OID 34691)
-- Dependencies: 176 176 2461
-- Name: pais_sigla_key; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY pais
    ADD CONSTRAINT pais_sigla_key UNIQUE (sigla);


--
-- TOC entry 2190 (class 2606 OID 34931)
-- Dependencies: 194 194 2461
-- Name: pk_arquivo; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY arquivo
    ADD CONSTRAINT pk_arquivo PRIMARY KEY (id);


--
-- TOC entry 2238 (class 2606 OID 35203)
-- Dependencies: 227 227 2461
-- Name: pk_auditoria_prospectiva; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY auditoria_prospectiva
    ADD CONSTRAINT pk_auditoria_prospectiva PRIMARY KEY (id);


--
-- TOC entry 2242 (class 2606 OID 35245)
-- Dependencies: 230 230 230 2461
-- Name: pk_auditoria_resultado_procedimento_auditoria; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY auditoria_resultado_procedimento_auditoria
    ADD CONSTRAINT pk_auditoria_resultado_procedimento_auditoria PRIMARY KEY (id_auditoria_prospectiva, id_resposta_procedimento_auditoria);


--
-- TOC entry 2202 (class 2606 OID 34937)
-- Dependencies: 201 201 2461
-- Name: pk_beneficiario; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY beneficiario
    ADD CONSTRAINT pk_beneficiario PRIMARY KEY (id);


--
-- TOC entry 2188 (class 2606 OID 34929)
-- Dependencies: 192 192 2461
-- Name: pk_cid; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY cid
    ADD CONSTRAINT pk_cid PRIMARY KEY (id);


--
-- TOC entry 2168 (class 2606 OID 34753)
-- Dependencies: 180 180 2461
-- Name: pk_cidade; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY cidade
    ADD CONSTRAINT pk_cidade PRIMARY KEY (id);


--
-- TOC entry 2220 (class 2606 OID 34949)
-- Dependencies: 211 211 2461
-- Name: pk_configuracao_edital_credenciado; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY configuracao_edital_credenciado
    ADD CONSTRAINT pk_configuracao_edital_credenciado PRIMARY KEY (id);


--
-- TOC entry 2222 (class 2606 OID 34951)
-- Dependencies: 213 213 2461
-- Name: pk_configuracao_edital_credenciado_procedimento; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY configuracao_edital_credenciado_procedimento
    ADD CONSTRAINT pk_configuracao_edital_credenciado_procedimento PRIMARY KEY (id);


--
-- TOC entry 2200 (class 2606 OID 34939)
-- Dependencies: 200 200 2461
-- Name: pk_convenio; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY convenio
    ADD CONSTRAINT pk_convenio PRIMARY KEY (id);


--
-- TOC entry 2148 (class 2606 OID 34741)
-- Dependencies: 169 169 2461
-- Name: pk_credenciado; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY credenciado
    ADD CONSTRAINT pk_credenciado PRIMARY KEY (id);


--
-- TOC entry 2206 (class 2606 OID 34941)
-- Dependencies: 203 203 2461
-- Name: pk_edital_credenciamento; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY edital_credenciamento
    ADD CONSTRAINT pk_edital_credenciamento PRIMARY KEY (id);


--
-- TOC entry 2152 (class 2606 OID 34745)
-- Dependencies: 173 173 2461
-- Name: pk_especialidade; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY especialidade
    ADD CONSTRAINT pk_especialidade PRIMARY KEY (id);


--
-- TOC entry 2166 (class 2606 OID 34751)
-- Dependencies: 178 178 2461
-- Name: pk_estado; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY estado
    ADD CONSTRAINT pk_estado PRIMARY KEY (id);


--
-- TOC entry 2210 (class 2606 OID 34945)
-- Dependencies: 205 205 2461
-- Name: pk_grupo; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY grupo
    ADD CONSTRAINT pk_grupo PRIMARY KEY (id);


--
-- TOC entry 2248 (class 2606 OID 35301)
-- Dependencies: 236 236 2461
-- Name: pk_historico_unidade_multiplicadora; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY historico_valor_unidade_multiplicadora
    ADD CONSTRAINT pk_historico_unidade_multiplicadora PRIMARY KEY (id);


--
-- TOC entry 2226 (class 2606 OID 35091)
-- Dependencies: 216 216 2461
-- Name: pk_historico_valor_configuracao_edital_credenciado; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY historico_valor_configuracao_edital_credenciado
    ADD CONSTRAINT pk_historico_valor_configuracao_edital_credenciado PRIMARY KEY (id);


--
-- TOC entry 2228 (class 2606 OID 35093)
-- Dependencies: 218 218 2461
-- Name: pk_historico_valor_configuracao_edital_procedimento; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY historico_valor_configuracao_edital_procedimento
    ADD CONSTRAINT pk_historico_valor_configuracao_edital_procedimento PRIMARY KEY (id);


--
-- TOC entry 2182 (class 2606 OID 34925)
-- Dependencies: 188 188 2461
-- Name: pk_historico_valor_porte; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY historico_valor_porte
    ADD CONSTRAINT pk_historico_valor_porte PRIMARY KEY (id);


--
-- TOC entry 2184 (class 2606 OID 34927)
-- Dependencies: 190 190 2461
-- Name: pk_historico_valor_porte_anestesico; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY historico_valor_porte_anestesico
    ADD CONSTRAINT pk_historico_valor_porte_anestesico PRIMARY KEY (id);


--
-- TOC entry 2150 (class 2606 OID 34743)
-- Dependencies: 171 171 2461
-- Name: pk_medico; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY medico
    ADD CONSTRAINT pk_medico PRIMARY KEY (id);


--
-- TOC entry 2154 (class 2606 OID 34747)
-- Dependencies: 174 174 174 2461
-- Name: pk_medico_especialidade; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY medico_especialidade
    ADD CONSTRAINT pk_medico_especialidade PRIMARY KEY (id_medico, id_especialidade);


--
-- TOC entry 2172 (class 2606 OID 34755)
-- Dependencies: 182 182 2461
-- Name: pk_organizacao_militar; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY organizacao_militar
    ADD CONSTRAINT pk_organizacao_militar PRIMARY KEY (id);


--
-- TOC entry 2160 (class 2606 OID 34749)
-- Dependencies: 176 176 2461
-- Name: pk_pais; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY pais
    ADD CONSTRAINT pk_pais PRIMARY KEY (id);


--
-- TOC entry 2178 (class 2606 OID 34759)
-- Dependencies: 185 185 2461
-- Name: pk_perfil; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY perfil
    ADD CONSTRAINT pk_perfil PRIMARY KEY (id);


--
-- TOC entry 2136 (class 2606 OID 34737)
-- Dependencies: 165 165 2461
-- Name: pk_porte; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY porte
    ADD CONSTRAINT pk_porte PRIMARY KEY (id);


--
-- TOC entry 2140 (class 2606 OID 34739)
-- Dependencies: 167 167 2461
-- Name: pk_porte_anestesico; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY porte_anestesico
    ADD CONSTRAINT pk_porte_anestesico PRIMARY KEY (id);


--
-- TOC entry 2194 (class 2606 OID 34935)
-- Dependencies: 198 198 2461
-- Name: pk_posto_graduacao; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY posto_graduacao
    ADD CONSTRAINT pk_posto_graduacao PRIMARY KEY (id);


--
-- TOC entry 2216 (class 2606 OID 34943)
-- Dependencies: 209 209 2461
-- Name: pk_procedimento; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY procedimento
    ADD CONSTRAINT pk_procedimento PRIMARY KEY (id);


--
-- TOC entry 2234 (class 2606 OID 35155)
-- Dependencies: 224 224 2461
-- Name: pk_procedimento_pedido_solicitacao; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY procedimento_pedido_solicitacao
    ADD CONSTRAINT pk_procedimento_pedido_solicitacao PRIMARY KEY (id);


--
-- TOC entry 2240 (class 2606 OID 35225)
-- Dependencies: 229 229 2461
-- Name: pk_resposta_procedimento_auditoria; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY resposta_procedimento_auditoria
    ADD CONSTRAINT pk_resposta_procedimento_auditoria PRIMARY KEY (id);


--
-- TOC entry 2192 (class 2606 OID 34933)
-- Dependencies: 196 196 2461
-- Name: pk_solicitacao; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY solicitacao
    ADD CONSTRAINT pk_solicitacao PRIMARY KEY (id);


--
-- TOC entry 2236 (class 2606 OID 35175)
-- Dependencies: 225 225 225 2461
-- Name: pk_solicitacao_pedidos_procedimento; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY solicitacao_pedidos_procedimento
    ADD CONSTRAINT pk_solicitacao_pedidos_procedimento PRIMARY KEY (id_solicitacao, id_procedimento_pedido_solicitacao);


--
-- TOC entry 2212 (class 2606 OID 34947)
-- Dependencies: 207 207 2461
-- Name: pk_subgrupo; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY subgrupo
    ADD CONSTRAINT pk_subgrupo PRIMARY KEY (id);


--
-- TOC entry 2224 (class 2606 OID 34953)
-- Dependencies: 214 214 2461
-- Name: pk_tipo_cobranca_credenciado_procedimento; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY tipo_cobranca_credenciado_procedimento
    ADD CONSTRAINT pk_tipo_cobranca_credenciado_procedimento PRIMARY KEY (id);


--
-- TOC entry 2230 (class 2606 OID 35136)
-- Dependencies: 220 220 2461
-- Name: pk_unidade_multiplicadora; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY unidade_multiplicadora
    ADD CONSTRAINT pk_unidade_multiplicadora PRIMARY KEY (id);


--
-- TOC entry 2174 (class 2606 OID 34757)
-- Dependencies: 184 184 2461
-- Name: pk_usuario; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT pk_usuario PRIMARY KEY (id);


--
-- TOC entry 2180 (class 2606 OID 34761)
-- Dependencies: 186 186 186 2461
-- Name: pk_usuario_perfil; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY usuario_perfil
    ADD CONSTRAINT pk_usuario_perfil PRIMARY KEY (id_usuario, id_perfil);


--
-- TOC entry 2142 (class 2606 OID 34650)
-- Dependencies: 167 167 2461
-- Name: porte_anestesico_codigo_key; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY porte_anestesico
    ADD CONSTRAINT porte_anestesico_codigo_key UNIQUE (codigo);


--
-- TOC entry 2138 (class 2606 OID 34641)
-- Dependencies: 165 165 2461
-- Name: porte_codigo_key; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY porte
    ADD CONSTRAINT porte_codigo_key UNIQUE (codigo);


--
-- TOC entry 2196 (class 2606 OID 35005)
-- Dependencies: 198 198 2461
-- Name: posto_graduacao_sigla_key; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY posto_graduacao
    ADD CONSTRAINT posto_graduacao_sigla_key UNIQUE (sigla);


--
-- TOC entry 2218 (class 2606 OID 34908)
-- Dependencies: 209 209 2461
-- Name: procedimento_codigo_key; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY procedimento
    ADD CONSTRAINT procedimento_codigo_key UNIQUE (codigo);


--
-- TOC entry 2214 (class 2606 OID 34896)
-- Dependencies: 207 207 2461
-- Name: subgrupo_codigo_key; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY subgrupo
    ADD CONSTRAINT subgrupo_codigo_key UNIQUE (codigo);


--
-- TOC entry 2262 (class 2606 OID 35390)
-- Dependencies: 244 244 2461
-- Name: uk_id_item_gab; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY gab_itens
    ADD CONSTRAINT uk_id_item_gab UNIQUE (id_item_gab);


--
-- TOC entry 2258 (class 2606 OID 35373)
-- Dependencies: 243 243 2461
-- Name: uk_id_metadado_valor_item_gab; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY item_gab_metadado_valor_item_gab
    ADD CONSTRAINT uk_id_metadado_valor_item_gab UNIQUE (id_metadado_valor_item_gab);


--
-- TOC entry 2176 (class 2606 OID 34728)
-- Dependencies: 184 184 2461
-- Name: usuario_login_key; Type: CONSTRAINT; Schema: sch_sisauc; Owner: sisauc; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_login_key UNIQUE (login);


--
-- TOC entry 2354 (class 2606 OID 35503)
-- Dependencies: 257 2275 258 2461
-- Name: auditoria_retrospectiva_metad_id_metadado_valor_auditoria__fkey; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY auditoria_retrospectiva_metadado_valor_auditoria_retrospectiva
    ADD CONSTRAINT auditoria_retrospectiva_metad_id_metadado_valor_auditoria__fkey FOREIGN KEY (id_metadado_valor_auditoria_retrospectiva) REFERENCES metadado_valor_auditoria_retrospectiva(id);


--
-- TOC entry 2355 (class 2606 OID 35508)
-- Dependencies: 2263 246 258 2461
-- Name: auditoria_retrospectiva_metadad_id_auditoria_retrospectiva_fkey; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY auditoria_retrospectiva_metadado_valor_auditoria_retrospectiva
    ADD CONSTRAINT auditoria_retrospectiva_metadad_id_auditoria_retrospectiva_fkey FOREIGN KEY (id_auditoria_retrospectiva) REFERENCES auditoria_retrospectiva(id);


--
-- TOC entry 2332 (class 2606 OID 35265)
-- Dependencies: 232 2147 169 2461
-- Name: empenho_id_credenciado_fkey; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY empenho
    ADD CONSTRAINT empenho_id_credenciado_fkey FOREIGN KEY (id_credenciado) REFERENCES credenciado(id);


--
-- TOC entry 2327 (class 2606 OID 35204)
-- Dependencies: 2173 227 184 2461
-- Name: fk_auditoria_prospectiva_usuario; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY auditoria_prospectiva
    ADD CONSTRAINT fk_auditoria_prospectiva_usuario FOREIGN KEY (id_usuario_auditor) REFERENCES usuario(id);


--
-- TOC entry 2330 (class 2606 OID 35246)
-- Dependencies: 230 227 2237 2461
-- Name: fk_auditoria_resultado_procedimento_auditoria_auditoria_prospec; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY auditoria_resultado_procedimento_auditoria
    ADD CONSTRAINT fk_auditoria_resultado_procedimento_auditoria_auditoria_prospec FOREIGN KEY (id_auditoria_prospectiva) REFERENCES auditoria_prospectiva(id);


--
-- TOC entry 2331 (class 2606 OID 35251)
-- Dependencies: 229 230 2239 2461
-- Name: fk_auditoria_resultado_procedimento_auditoria_resposta_procedim; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY auditoria_resultado_procedimento_auditoria
    ADD CONSTRAINT fk_auditoria_resultado_procedimento_auditoria_resposta_procedim FOREIGN KEY (id_resposta_procedimento_auditoria) REFERENCES resposta_procedimento_auditoria(id);


--
-- TOC entry 2328 (class 2606 OID 35209)
-- Dependencies: 2191 196 227 2461
-- Name: fk_auditoria_solicitacao; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY auditoria_prospectiva
    ADD CONSTRAINT fk_auditoria_solicitacao FOREIGN KEY (id_solicitacao_procedimento) REFERENCES solicitacao(id);


--
-- TOC entry 2305 (class 2606 OID 35016)
-- Dependencies: 201 2201 201 2461
-- Name: fk_beneficiario_beneficiario_titular; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY beneficiario
    ADD CONSTRAINT fk_beneficiario_beneficiario_titular FOREIGN KEY (id_beneficiario_titular) REFERENCES beneficiario(id);


--
-- TOC entry 2306 (class 2606 OID 35021)
-- Dependencies: 201 200 2199 2461
-- Name: fk_beneficiario_convenio; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY beneficiario
    ADD CONSTRAINT fk_beneficiario_convenio FOREIGN KEY (id_convenio) REFERENCES convenio(id);


--
-- TOC entry 2303 (class 2606 OID 35006)
-- Dependencies: 182 2171 201 2461
-- Name: fk_beneficiario_organizacao_militar; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY beneficiario
    ADD CONSTRAINT fk_beneficiario_organizacao_militar FOREIGN KEY (id_organizacao_militar) REFERENCES organizacao_militar(id);


--
-- TOC entry 2304 (class 2606 OID 35011)
-- Dependencies: 198 2193 201 2461
-- Name: fk_beneficiario_posto_graducacao; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY beneficiario
    ADD CONSTRAINT fk_beneficiario_posto_graducacao FOREIGN KEY (id_posto_graduacao) REFERENCES posto_graduacao(id);


--
-- TOC entry 2288 (class 2606 OID 34782)
-- Dependencies: 180 2165 178 2461
-- Name: fk_cidade_estado; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY cidade
    ADD CONSTRAINT fk_cidade_estado FOREIGN KEY (id_estado) REFERENCES estado(id);


--
-- TOC entry 2312 (class 2606 OID 35053)
-- Dependencies: 169 211 2147 2461
-- Name: fk_configuracao_edital_credenciado_credenciado; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY configuracao_edital_credenciado
    ADD CONSTRAINT fk_configuracao_edital_credenciado_credenciado FOREIGN KEY (id_credenciado) REFERENCES credenciado(id);


--
-- TOC entry 2311 (class 2606 OID 35048)
-- Dependencies: 2205 203 211 2461
-- Name: fk_configuracao_edital_credenciado_edital_credenciamento; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY configuracao_edital_credenciado
    ADD CONSTRAINT fk_configuracao_edital_credenciado_edital_credenciamento FOREIGN KEY (id_edital_credenciamento) REFERENCES edital_credenciamento(id);


--
-- TOC entry 2314 (class 2606 OID 35063)
-- Dependencies: 2219 211 213 2461
-- Name: fk_configuracao_edital_credenciado_procedimento_configuracao_ed; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY configuracao_edital_credenciado_procedimento
    ADD CONSTRAINT fk_configuracao_edital_credenciado_procedimento_configuracao_ed FOREIGN KEY (id_configuracao_edital_credenciado) REFERENCES configuracao_edital_credenciado(id);


--
-- TOC entry 2316 (class 2606 OID 35073)
-- Dependencies: 173 213 2151 2461
-- Name: fk_configuracao_edital_credenciado_procedimento_especialidade; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY configuracao_edital_credenciado_procedimento
    ADD CONSTRAINT fk_configuracao_edital_credenciado_procedimento_especialidade FOREIGN KEY (id_especialidade) REFERENCES especialidade(id);


--
-- TOC entry 2315 (class 2606 OID 35068)
-- Dependencies: 213 209 2215 2461
-- Name: fk_configuracao_edital_credenciado_procedimento_procedimento; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY configuracao_edital_credenciado_procedimento
    ADD CONSTRAINT fk_configuracao_edital_credenciado_procedimento_procedimento FOREIGN KEY (id_procedimento) REFERENCES procedimento(id);


--
-- TOC entry 2313 (class 2606 OID 35058)
-- Dependencies: 214 2223 213 2461
-- Name: fk_configuracao_edital_credenciado_procedimento_tipo_cobranca; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY configuracao_edital_credenciado_procedimento
    ADD CONSTRAINT fk_configuracao_edital_credenciado_procedimento_tipo_cobranca FOREIGN KEY (id_tipo_cobranca) REFERENCES tipo_cobranca_credenciado_procedimento(id);


--
-- TOC entry 2283 (class 2606 OID 34762)
-- Dependencies: 180 169 2167 2461
-- Name: fk_credenciado_cidade; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY credenciado
    ADD CONSTRAINT fk_credenciado_cidade FOREIGN KEY (id_cidade) REFERENCES cidade(id);


--
-- TOC entry 2356 (class 2606 OID 35559)
-- Dependencies: 201 260 2201 2461
-- Name: fk_desconto_beneficiario_beneficiario; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY desconto_beneficiario
    ADD CONSTRAINT fk_desconto_beneficiario_beneficiario FOREIGN KEY (id_beneficiario) REFERENCES beneficiario(id);


--
-- TOC entry 2358 (class 2606 OID 35574)
-- Dependencies: 2279 261 260 2461
-- Name: fk_desconto_beneficiario_itens; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY desconto_beneficiario_itens
    ADD CONSTRAINT fk_desconto_beneficiario_itens FOREIGN KEY (id_desconto_beneficiario) REFERENCES desconto_beneficiario(id);


--
-- TOC entry 2286 (class 2606 OID 34772)
-- Dependencies: 174 2149 171 2461
-- Name: fk_especialidade_medico; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY medico_especialidade
    ADD CONSTRAINT fk_especialidade_medico FOREIGN KEY (id_medico) REFERENCES medico(id);


--
-- TOC entry 2287 (class 2606 OID 34777)
-- Dependencies: 178 176 2159 2461
-- Name: fk_estado_pais; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY estado
    ADD CONSTRAINT fk_estado_pais FOREIGN KEY (id_pais) REFERENCES pais(id);


--
-- TOC entry 2338 (class 2606 OID 35323)
-- Dependencies: 2237 238 227 2461
-- Name: fk_gab_auditoria_prospectiva; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY gab
    ADD CONSTRAINT fk_gab_auditoria_prospectiva FOREIGN KEY (id_auditoria_prospectiva) REFERENCES auditoria_prospectiva(id);


--
-- TOC entry 2341 (class 2606 OID 35338)
-- Dependencies: 2201 201 238 2461
-- Name: fk_gab_beneficiario; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY gab
    ADD CONSTRAINT fk_gab_beneficiario FOREIGN KEY (id_beneficiario) REFERENCES beneficiario(id);


--
-- TOC entry 2340 (class 2606 OID 35333)
-- Dependencies: 169 238 2147 2461
-- Name: fk_gab_credenciado; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY gab
    ADD CONSTRAINT fk_gab_credenciado FOREIGN KEY (id_credenciado) REFERENCES credenciado(id);


--
-- TOC entry 2347 (class 2606 OID 35396)
-- Dependencies: 2249 244 238 2461
-- Name: fk_gab_itens_gab; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY gab_itens
    ADD CONSTRAINT fk_gab_itens_gab FOREIGN KEY (id_gab) REFERENCES gab(id);


--
-- TOC entry 2346 (class 2606 OID 35391)
-- Dependencies: 2251 244 240 2461
-- Name: fk_gab_itens_item_gab; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY gab_itens
    ADD CONSTRAINT fk_gab_itens_item_gab FOREIGN KEY (id_item_gab) REFERENCES item_gab(id);


--
-- TOC entry 2339 (class 2606 OID 35328)
-- Dependencies: 2171 238 182 2461
-- Name: fk_gab_organizacao_militar; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY gab
    ADD CONSTRAINT fk_gab_organizacao_militar FOREIGN KEY (id_organizacao_militar) REFERENCES organizacao_militar(id);


--
-- TOC entry 2337 (class 2606 OID 35307)
-- Dependencies: 184 2173 236 2461
-- Name: fk_historico_unidade_multiplicadora_usuario; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY historico_valor_unidade_multiplicadora
    ADD CONSTRAINT fk_historico_unidade_multiplicadora_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id);


--
-- TOC entry 2318 (class 2606 OID 35099)
-- Dependencies: 169 216 2147 2461
-- Name: fk_historico_valor_configuracao_edital_credenciado_credenciado; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY historico_valor_configuracao_edital_credenciado
    ADD CONSTRAINT fk_historico_valor_configuracao_edital_credenciado_credenciado FOREIGN KEY (id_credenciado) REFERENCES credenciado(id);


--
-- TOC entry 2317 (class 2606 OID 35094)
-- Dependencies: 211 216 2219 2461
-- Name: fk_historico_valor_configuracao_edital_credenciado_id_edital_cr; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY historico_valor_configuracao_edital_credenciado
    ADD CONSTRAINT fk_historico_valor_configuracao_edital_credenciado_id_edital_cr FOREIGN KEY (id_configuracao_edital_credenciamento) REFERENCES configuracao_edital_credenciado(id);


--
-- TOC entry 2320 (class 2606 OID 35109)
-- Dependencies: 2223 214 218 2461
-- Name: fk_historico_valor_configuracao_edital_credenciado_procedimento; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY historico_valor_configuracao_edital_procedimento
    ADD CONSTRAINT fk_historico_valor_configuracao_edital_credenciado_procedimento FOREIGN KEY (id_tipo_cobranca) REFERENCES tipo_cobranca_credenciado_procedimento(id);


--
-- TOC entry 2319 (class 2606 OID 35104)
-- Dependencies: 2173 184 216 2461
-- Name: fk_historico_valor_configuracao_edital_credenciado_usuario; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY historico_valor_configuracao_edital_credenciado
    ADD CONSTRAINT fk_historico_valor_configuracao_edital_credenciado_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id);


--
-- TOC entry 2323 (class 2606 OID 35124)
-- Dependencies: 218 213 2221 2461
-- Name: fk_historico_valor_configuracao_edital_procedimento_configuraca; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY historico_valor_configuracao_edital_procedimento
    ADD CONSTRAINT fk_historico_valor_configuracao_edital_procedimento_configuraca FOREIGN KEY (id_configuracao_edital_credenciado_procedimento) REFERENCES configuracao_edital_credenciado_procedimento(id);


--
-- TOC entry 2322 (class 2606 OID 35119)
-- Dependencies: 2151 173 218 2461
-- Name: fk_historico_valor_configuracao_edital_procedimento_especialida; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY historico_valor_configuracao_edital_procedimento
    ADD CONSTRAINT fk_historico_valor_configuracao_edital_procedimento_especialida FOREIGN KEY (id_especialidade) REFERENCES especialidade(id);


--
-- TOC entry 2321 (class 2606 OID 35114)
-- Dependencies: 209 218 2215 2461
-- Name: fk_historico_valor_configuracao_edital_procedimento_id_procedim; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY historico_valor_configuracao_edital_procedimento
    ADD CONSTRAINT fk_historico_valor_configuracao_edital_procedimento_id_procedim FOREIGN KEY (id_procedimento) REFERENCES procedimento(id);


--
-- TOC entry 2295 (class 2606 OID 34964)
-- Dependencies: 190 2139 167 2461
-- Name: fk_historico_valor_porte_anestesico_id_porte_anestesico; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY historico_valor_porte_anestesico
    ADD CONSTRAINT fk_historico_valor_porte_anestesico_id_porte_anestesico FOREIGN KEY (id_porte_anestesico) REFERENCES porte_anestesico(id);


--
-- TOC entry 2296 (class 2606 OID 34969)
-- Dependencies: 184 190 2173 2461
-- Name: fk_historico_valor_porte_anestesico_usuario; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY historico_valor_porte_anestesico
    ADD CONSTRAINT fk_historico_valor_porte_anestesico_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id);


--
-- TOC entry 2293 (class 2606 OID 34954)
-- Dependencies: 165 188 2135 2461
-- Name: fk_historico_valor_porte_id_porte; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY historico_valor_porte
    ADD CONSTRAINT fk_historico_valor_porte_id_porte FOREIGN KEY (id_porte) REFERENCES porte(id);


--
-- TOC entry 2294 (class 2606 OID 34959)
-- Dependencies: 2173 184 188 2461
-- Name: fk_historico_valor_porte_usuario; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY historico_valor_porte
    ADD CONSTRAINT fk_historico_valor_porte_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id);


--
-- TOC entry 2336 (class 2606 OID 35302)
-- Dependencies: 2229 236 220 2461
-- Name: fk_historico_valor_unidade_multiplicadora; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY historico_valor_unidade_multiplicadora
    ADD CONSTRAINT fk_historico_valor_unidade_multiplicadora FOREIGN KEY (id_unidade_multiplicadora) REFERENCES unidade_multiplicadora(id);


--
-- TOC entry 2352 (class 2606 OID 35464)
-- Dependencies: 2251 253 240 2461
-- Name: fk_id_item_gab; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY lote_itens
    ADD CONSTRAINT fk_id_item_gab FOREIGN KEY (id_item_gab) REFERENCES item_gab(id);

--
-- TOC entry ? (class ? OID ?)
-- Dependencies: ? ? ? ?
-- Name: fk_id_item_ar; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY lote_ressarcimento_itens
    ADD CONSTRAINT fk_id_item_ar FOREIGN KEY (id_item_ar) REFERENCES item_ar(id);

--
-- TOC entry 2357 (class 2606 OID 35569)
-- Dependencies: 261 240 2251 2461
-- Name: fk_id_item_gab; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY desconto_beneficiario_itens
    ADD CONSTRAINT fk_id_item_gab FOREIGN KEY (id_item_gab) REFERENCES item_gab(id);


--
-- TOC entry 2342 (class 2606 OID 35351)
-- Dependencies: 213 2221 240 2461
-- Name: fk_item_gab_configuracao_edital_credenciado_procedimento; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY item_gab
    ADD CONSTRAINT fk_item_gab_configuracao_edital_credenciado_procedimento FOREIGN KEY (id_configuracao_edital_credenciado_procedimento) REFERENCES configuracao_edital_credenciado_procedimento(id);


--
-- TOC entry 2345 (class 2606 OID 35379)
-- Dependencies: 240 243 2251 2461
-- Name: fk_item_gab_metadado_valor_item_gab_item_gab; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY item_gab_metadado_valor_item_gab
    ADD CONSTRAINT fk_item_gab_metadado_valor_item_gab_item_gab FOREIGN KEY (id_item_gab) REFERENCES item_gab(id);


--
-- TOC entry 2344 (class 2606 OID 35374)
-- Dependencies: 242 243 2253 2461
-- Name: fk_item_gab_metadado_valor_item_gab_metadado_valor_item_gab; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY item_gab_metadado_valor_item_gab
    ADD CONSTRAINT fk_item_gab_metadado_valor_item_gab_metadado_valor_item_gab FOREIGN KEY (id_metadado_valor_item_gab) REFERENCES metadado_valor_item_gab(id);


--
-- TOC entry 2350 (class 2606 OID 35454)
-- Dependencies: 252 2147 169 2461
-- Name: fk_lote_credenciado; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY lote
    ADD CONSTRAINT fk_lote_credenciado FOREIGN KEY (id_credenciado) REFERENCES credenciado(id);


--
-- TOC entry 2353 (class 2606 OID 35469)
-- Dependencies: 252 253 2269 2461
-- Name: fk_lote_itens; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY lote_itens
    ADD CONSTRAINT fk_lote_itens FOREIGN KEY (id_lote) REFERENCES lote(id);


--
-- TOC entry 2285 (class 2606 OID 34767)
-- Dependencies: 173 2151 174 2461
-- Name: fk_medico_especialidade; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY medico_especialidade
    ADD CONSTRAINT fk_medico_especialidade FOREIGN KEY (id_especialidade) REFERENCES especialidade(id);


--
-- TOC entry 2284 (class 2606 OID 34807)
-- Dependencies: 182 2171 171 2461
-- Name: fk_medico_organizacao_militar; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY medico
    ADD CONSTRAINT fk_medico_organizacao_militar FOREIGN KEY (id_organizacao_militar) REFERENCES organizacao_militar(id);


--
-- TOC entry 2289 (class 2606 OID 34787)
-- Dependencies: 180 182 2167 2461
-- Name: fk_organizacao_militar_cidade; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY organizacao_militar
    ADD CONSTRAINT fk_organizacao_militar_cidade FOREIGN KEY (id_cidade) REFERENCES cidade(id);


--
-- TOC entry 2324 (class 2606 OID 35166)
-- Dependencies: 224 213 2221 2461
-- Name: fk_procedimento_pedido_solicitacao_credenciado; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY procedimento_pedido_solicitacao
    ADD CONSTRAINT fk_procedimento_pedido_solicitacao_credenciado FOREIGN KEY (id_configuracao_edital_credenciado_procedimento) REFERENCES configuracao_edital_credenciado_procedimento(id);


--
-- TOC entry 2308 (class 2606 OID 35028)
-- Dependencies: 209 165 2135 2461
-- Name: fk_procedimento_porte; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY procedimento
    ADD CONSTRAINT fk_procedimento_porte FOREIGN KEY (id_porte) REFERENCES porte(id);


--
-- TOC entry 2309 (class 2606 OID 35033)
-- Dependencies: 167 209 2139 2461
-- Name: fk_procedimento_porte_anestesico; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY procedimento
    ADD CONSTRAINT fk_procedimento_porte_anestesico FOREIGN KEY (id_porte_anestesico) REFERENCES porte_anestesico(id);


--
-- TOC entry 2310 (class 2606 OID 35038)
-- Dependencies: 207 2211 209 2461
-- Name: fk_procedimento_subgrupo; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY procedimento
    ADD CONSTRAINT fk_procedimento_subgrupo FOREIGN KEY (id_subgrupo) REFERENCES subgrupo(id);


--
-- TOC entry 2329 (class 2606 OID 35236)
-- Dependencies: 213 2221 229 2461
-- Name: fk_resposta_procedimento_auditoria_procedimento_configuracao_ed; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY resposta_procedimento_auditoria
    ADD CONSTRAINT fk_resposta_procedimento_auditoria_procedimento_configuracao_ed FOREIGN KEY (id_configuracao_edital_credenciado_procedimento) REFERENCES configuracao_edital_credenciado_procedimento(id);


--
-- TOC entry 2301 (class 2606 OID 34994)
-- Dependencies: 194 196 2189 2461
-- Name: fk_solicitacao_arquivo; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY solicitacao
    ADD CONSTRAINT fk_solicitacao_arquivo FOREIGN KEY (id_arquivo) REFERENCES arquivo(id);


--
-- TOC entry 2298 (class 2606 OID 34979)
-- Dependencies: 196 2201 201 2461
-- Name: fk_solicitacao_beneficiario; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY solicitacao
    ADD CONSTRAINT fk_solicitacao_beneficiario FOREIGN KEY (id_beneficiario) REFERENCES beneficiario(id);


--
-- TOC entry 2302 (class 2606 OID 34999)
-- Dependencies: 2187 196 192 2461
-- Name: fk_solicitacao_cid; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY solicitacao
    ADD CONSTRAINT fk_solicitacao_cid FOREIGN KEY (id_cid) REFERENCES cid(id);


--
-- TOC entry 2300 (class 2606 OID 34989)
-- Dependencies: 2149 171 196 2461
-- Name: fk_solicitacao_medico; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY solicitacao
    ADD CONSTRAINT fk_solicitacao_medico FOREIGN KEY (id_medico) REFERENCES medico(id);


--
-- TOC entry 2297 (class 2606 OID 34974)
-- Dependencies: 196 2171 182 2461
-- Name: fk_solicitacao_organizacao_militar; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY solicitacao
    ADD CONSTRAINT fk_solicitacao_organizacao_militar FOREIGN KEY (id_organizacao_militar) REFERENCES organizacao_militar(id);


--
-- TOC entry 2326 (class 2606 OID 35181)
-- Dependencies: 225 224 2233 2461
-- Name: fk_solicitacao_pedidos_procedimento_id_pedido; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY solicitacao_pedidos_procedimento
    ADD CONSTRAINT fk_solicitacao_pedidos_procedimento_id_pedido FOREIGN KEY (id_procedimento_pedido_solicitacao) REFERENCES procedimento_pedido_solicitacao(id);


--
-- TOC entry 2325 (class 2606 OID 35176)
-- Dependencies: 2191 225 196 2461
-- Name: fk_solicitacao_pedidos_procedimento_id_solicitacao; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY solicitacao_pedidos_procedimento
    ADD CONSTRAINT fk_solicitacao_pedidos_procedimento_id_solicitacao FOREIGN KEY (id_solicitacao) REFERENCES solicitacao(id);


--
-- TOC entry 2299 (class 2606 OID 34984)
-- Dependencies: 196 184 2173 2461
-- Name: fk_solicitacao_usuario; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY solicitacao
    ADD CONSTRAINT fk_solicitacao_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id);


--
-- TOC entry 2307 (class 2606 OID 35043)
-- Dependencies: 2209 207 205 2461
-- Name: fk_subgrupo_grupo; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY subgrupo
    ADD CONSTRAINT fk_subgrupo_grupo FOREIGN KEY (id_grupo) REFERENCES grupo(id);


--
-- TOC entry 2290 (class 2606 OID 34792)
-- Dependencies: 182 2171 184 2461
-- Name: fk_usuario_organizacao_militar; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT fk_usuario_organizacao_militar FOREIGN KEY (id_organizacao_militar) REFERENCES organizacao_militar(id);


--
-- TOC entry 2292 (class 2606 OID 34802)
-- Dependencies: 186 2177 185 2461
-- Name: fk_usuario_perfil_id_perfil; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY usuario_perfil
    ADD CONSTRAINT fk_usuario_perfil_id_perfil FOREIGN KEY (id_perfil) REFERENCES perfil(id);


--
-- TOC entry 2291 (class 2606 OID 34797)
-- Dependencies: 2173 186 184 2461
-- Name: fk_usuario_perfil_id_usuario; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY usuario_perfil
    ADD CONSTRAINT fk_usuario_perfil_id_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id);


--
-- TOC entry 2348 (class 2606 OID 35423)
-- Dependencies: 2263 248 246 2461
-- Name: historico_auditoria_retrospecti_id_auditoria_retrospectiva_fkey; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY historico_auditoria_retrospectiva
    ADD CONSTRAINT historico_auditoria_retrospecti_id_auditoria_retrospectiva_fkey FOREIGN KEY (id_auditoria_retrospectiva) REFERENCES auditoria_retrospectiva(id);


--
-- TOC entry 2349 (class 2606 OID 35428)
-- Dependencies: 248 184 2173 2461
-- Name: historico_auditoria_retrospectiva_id_usuario_fkey; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY historico_auditoria_retrospectiva
    ADD CONSTRAINT historico_auditoria_retrospectiva_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES usuario(id);


--
-- TOC entry 2333 (class 2606 OID 35279)
-- Dependencies: 234 2147 169 2461
-- Name: historico_empenho_id_credenciado_fkey; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY historico_empenho
    ADD CONSTRAINT historico_empenho_id_credenciado_fkey FOREIGN KEY (id_credenciado) REFERENCES credenciado(id);


--
-- TOC entry 2334 (class 2606 OID 35284)
-- Dependencies: 232 2243 234 2461
-- Name: historico_empenho_id_empenho_fkey; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY historico_empenho
    ADD CONSTRAINT historico_empenho_id_empenho_fkey FOREIGN KEY (id_empenho) REFERENCES empenho(id);


--
-- TOC entry 2335 (class 2606 OID 35289)
-- Dependencies: 184 2173 234 2461
-- Name: historico_empenho_id_usuario_fkey; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY historico_empenho
    ADD CONSTRAINT historico_empenho_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES usuario(id);


--
-- TOC entry 2343 (class 2606 OID 35433)
-- Dependencies: 246 240 2263 2461
-- Name: item_gab_id_auditoria_retrospectiva_fkey; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY item_gab
    ADD CONSTRAINT item_gab_id_auditoria_retrospectiva_fkey FOREIGN KEY (id_auditoria_retrospectiva) REFERENCES auditoria_retrospectiva(id);


--
-- TOC entry 2351 (class 2606 OID 35482)
-- Dependencies: 252 2273 255 2461
-- Name: lote_id_nota_fiscal_fkey; Type: FK CONSTRAINT; Schema: sch_sisauc; Owner: sisauc
--

ALTER TABLE ONLY lote
    ADD CONSTRAINT lote_id_nota_fiscal_fkey FOREIGN KEY (id_nota_fiscal) REFERENCES nota_fiscal(id);


-- Completed on 2014-10-30 09:16:34 BRST

--
-- PostgreSQL database dump complete
--

