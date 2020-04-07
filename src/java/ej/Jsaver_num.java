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
public class Jsaver_num implements Jsaver_numLocal {

    @PersistenceContext
    EntityManager em;

    @Override
    public Collection<Score> getList(String login) {
        Users u = em.find(Users.class, login);
        // Если пользователь найден, то возвращается список его сообщений, в 
        // противном случае возвращается значение null
        return u != null ? u.getScoreCollection() : null;

    }

    @Override
    public boolean addMessage(String login, Integer number) {
        if (login == null || login.trim().isEmpty() || number == null) {
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
            Score s = new Score(u, number);
            // Добавление сообщения к списку сообщений пользователя.
            u.getScoreCollection().add(s);
            // Добавление сообщения и пользователя к контексту EntityManager
            if (!em.contains(u)) {
                em.persist(u);
            }
            em.persist(s);
            // Обновление записей в базе данных.
            em.merge(s);
            em.merge(u);
            return true;
        } catch (Exception e) {
            System.out.println("ghg");
            return false;
        }
    }
}
