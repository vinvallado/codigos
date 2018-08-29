package br.mil.fab.consigext.repository;

import java.io.Serializable;

//import org.springframework.data.jpa.datatables.qrepository.QDataTablesRepository;
//import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean //Impede que o framework tente criar instancia na inicialização 
public interface GenericRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T>, PagingAndSortingRepository<T, ID>{ 
//, DataTablesRepository<T, ID> {

}
