/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package FirearmsEvent;

import java.util.EventListener;

/**
 *
 * @author trbrenn
 * @date 1-2-2025
 */
public interface FirearmListener extends EventListener {
    public void FirearmEventOccurred(FirearmEvent e);
}
