/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ej;

import java.util.Collection;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ann
 */
@Singleton
public class JSaver implements JSaverLocal {

    @PersistenceContext
    EntityManager em;

public boolean addMessage(String login, String message) {
        if (login == null || login.trim().isEmpty() || message == null) {
            return false;
        }
        try {
            // Поиск записи по логину пользователя.
            Users u = em.find(Users.class, login);
            if (u == null) {
                // Если запись не найдена, то создаётся новый объект типа Users.
                u = new Users(login);
            }
            // Создание нового объекта с текстом сообщения.
            Messages m = new Messages(u, message);
            // Добавление сообщения к списку сообщений пользователя.
            u.getMessagesCollection().add(m);
            // Добавление сообщения и пользователя к контексту EntityManager
            if (!em.contains(u)) {
                em.persist(u);
            }
            em.persist(m);
            // Обновление записей в базе данных.
            em.merge(m);
            em.merge(u);
            return true;
        } catch (Exception e) {
            System.out.println("ghg");;
            return false;
        }
    }
public Collection<Messages> getList(String login) {
        // Поиск записи по логину пользователя.hgh
        Users u = em.find(Users.class, login);
        // Если пользователь найден, то возвращается список его сообщений, в 
        // противном случае возвращается значение null
        return u != null ? u.getMessagesCollection() : null;
    }
}
