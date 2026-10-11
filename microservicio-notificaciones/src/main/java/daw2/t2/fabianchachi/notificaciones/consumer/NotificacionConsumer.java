package daw2.t2.fabianchachi.notificaciones.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificacionConsumer {

    @RabbitListener(queues = "cola_notificaciones_citas")
    public void recibirEventoCita(Object citaObj) {
        System.out.println("================ AUDITORÍA Y NOTIFICACIÓN ===============");
        System.out.println("¡Evento recibido de RabbitMQ: CITA CONFIRMADA!");
        System.out.println("Detalles de la cita: " + citaObj);
        System.out.println("Enviando aviso por correo/SMS al paciente de forma asíncrona...");
        System.out.println("=========================================================");
    }
}