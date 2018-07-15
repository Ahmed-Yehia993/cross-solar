package com.crossover.techtrial.service;

import com.crossover.techtrial.model.Panel;

/**
 * PanelService interface for Panels.
 * @author Ahmed Yehia
 *
 */
public interface PanelService {
  
  /**
   * Register a panel for electricity monitoring.
   * @param panel to register with system.
   */
  
  void register(Panel panel);
  
  Panel findBySerial(String serial);
}
