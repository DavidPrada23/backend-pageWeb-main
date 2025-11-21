package com.apiMedicMax.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiMedicMax.repositories.FacturaRepository;
import com.apiMedicMax.models.Pedido;
import com.apiMedicMax.models.Factura;

@Service
public class FacturaService {
    
    @Autowired
    private FacturaRepository facturaRepository;

    public Factura generarFactura(Pedido pedido){
        Factura factura = new Factura();
        factura.setPedido(pedido);
        factura.setFechaEmision(new Date());
        factura.setTotal(pedido.calcularTotal());
        return facturaRepository.save(factura);
    }

    public Factura getFacturaById(Long id){
        return facturaRepository.findById(id).orElse(null);
    }
}
