/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package PowderEvent;

import java.util.EventListener;

/**
 *
 * @author trbrenn
 */
public interface PowderListener  extends EventListener {
    public void PowderEventOccurred(PowderEvent e);
}
