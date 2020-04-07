/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ej;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author ann
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "MyTopic")
    ,
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "MyTopic")
    ,
        @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable")
    ,
        @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "MyTopic")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
})
public class TextMDB implements MessageListener {
    @EJB
    private JSaverLocal jpa;
    public TextMDB() {
    }
    
    @Override
    public void onMessage(Message message) {
    try {
            // Проверка соответствия типа сообщения ожидаемому типу
            if (message instanceof TextMessage) {
                TextMessage msg = (TextMessage) message;
                // Извлечение значения ключа login
                String login = msg.getStringProperty("login");
                // Извлечение значения ключа message
                String text = msg.getText();
                // Передача полученного сообщения для записи в базу данных.
                jpa.addMessage(login, text);
            }
        } catch (JMSException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
}
