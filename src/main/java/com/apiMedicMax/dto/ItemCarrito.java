package com.apiMedicMax.dto;

import com.apiMedicMax.models.Producto;

public class ItemCarrito {
    private Producto producto;
    private int cantidad;

    public ItemCarrito(Producto producto, int cantidad){
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }

    public void setProducto (Producto producto) { this.producto = producto; }
    public void setCantidad (int cantidad) { this.cantidad = cantidad; }
}
