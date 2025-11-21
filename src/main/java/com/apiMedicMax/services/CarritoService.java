package com.apiMedicMax.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiMedicMax.models.Producto;
import com.apiMedicMax.repositories.ProductoRepository;
import com.apiMedicMax.dto.ItemCarrito;
import com.apiMedicMax.models.ItemPedido;
import com.apiMedicMax.models.Pedido;

@Service
public class CarritoService {
    @Autowired
    private ProductoRepository productoRepository;
    
    private final List<ItemCarrito> carrito = new ArrayList<>();

    public void agregarProductoPorId(Long productoId, int cantidad){
        // Agregamos al carrito el producto con la cantidad
        Producto producto = productoRepository.findById(productoId)
                            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        carrito.add(new ItemCarrito(producto, cantidad));
        
    }

    public List<ItemCarrito> obtenerCarrito(){
        return new ArrayList<>(carrito); // Se retorna una copia para evitar modificación externas
    }

    public boolean eliminarProducto(Long productoId){
        return carrito.removeIf(item -> item.getProducto().getId().equals(productoId));
    }

    public Pedido realizarCheckout(){
        Pedido pedido = new Pedido();
        pedido.setEstado("Pendiente");
        pedido.setFechaPedido(new Date());

        List<ItemPedido> itemsPedido = new ArrayList<>();
        for (ItemCarrito item: carrito){
            ItemPedido ip = new ItemPedido();
            ip.setProducto(item.getProducto());
            ip.setCantidad(item.getCantidad());
            ip.setPedido(pedido);
            itemsPedido.add(ip);
        }
        pedido.setItems(itemsPedido);
        carrito.clear();

        return pedido;
    }
}
