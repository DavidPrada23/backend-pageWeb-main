package com.apiMedicMax.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiMedicMax.repositories.PedidoRepository;
import com.apiMedicMax.models.Pedido;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido createPedido(Pedido pedido){
        return pedidoRepository.save(pedido);
    }
    
    public List<Pedido> getAllPedidos(){
        return pedidoRepository.findAll();
    }

    public Pedido getPedidoById(Long id){
        return pedidoRepository.findById(id).orElse(null);
    }

    public List<Pedido> getPedidosPagados() {
        return pedidoRepository.findByEstadoIgnoreCase("PAGADO");
    }

    public Pedido updatePedido(Long id, Pedido pedidoData){
        Optional<Pedido> optionalPedido = pedidoRepository.findById(id);
        if (optionalPedido.isPresent()){
            Pedido pedidoExistente = optionalPedido.get();
            // Actualizar aquí los campos que quieras cambiar
            pedidoExistente.setEstado(pedidoData.getEstado());
            pedidoExistente.setFechaPedido(pedidoData.getFechaPedido());
            // Puedes actualizar más datos del periodo si quieres

            return pedidoRepository.save(pedidoExistente);
        } else {
            return null;
        }
    }

    public boolean deletePedido(Long id) {
        if (pedidoRepository.existsById(id)){
            pedidoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
