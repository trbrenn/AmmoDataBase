/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package PrimerEvent;

import CasesEvent.CasesEvent;
import java.util.EventListener;

/**
 *
 * @author trbrenn
 */
public interface PrimerListener  extends EventListener {
    public void PrimerEventOccurred(PrimerEvent e);
}
