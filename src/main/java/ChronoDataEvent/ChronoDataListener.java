/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ChronoDataEvent;

import java.util.EventListener;

/**
 *
 * @author trbrenn
 * @date 1-4-25
 */
public interface ChronoDataListener extends EventListener {
    public void ChronoDataEventOccurred(ChronoDataEvent cde);
}
