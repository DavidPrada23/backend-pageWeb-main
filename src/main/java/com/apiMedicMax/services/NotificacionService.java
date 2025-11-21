package com.apiMedicMax.services;

import org.springframework.stereotype.Service;

@Service
public class NotificacionService {

    public void enviarNotificacion(String mensaje, String destinatario){
        System.out.println("Notificación enviada a " +destinatario + ":" + mensaje);
    }
}
