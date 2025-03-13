/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package BulletsEvent;

import java.util.EventListener;

/**
 *
 * @author trbrenn
 * @date 12-24-2024
 */
public interface BulletsListener extends EventListener {
    public void BulletsEventOccurred(BulletsEvent e);
}
