/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ej;

import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author ann
 */
@Local
public interface JSaverLocal {

    Collection<Messages> getList(String login);

    boolean addMessage(String login, String message); 
}
