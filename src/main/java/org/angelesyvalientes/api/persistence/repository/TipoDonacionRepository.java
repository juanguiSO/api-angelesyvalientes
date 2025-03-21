package org.angelesyvalientes.api.persistence.repository;


import org.angelesyvalientes.api.persistence.entity.TipoDonacion;
import org.angelesyvalientes.api.service.TipoDonacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoDonacionRepository extends JpaRepository<TipoDonacion, Long>  {



}
