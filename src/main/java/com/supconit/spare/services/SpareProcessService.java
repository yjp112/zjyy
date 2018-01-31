package com.supconit.spare.services;

import com.supconit.spare.entities.Inventory;
import com.supconit.spare.entities.StockBack;
import com.supconit.spare.entities.StockIn;
import com.supconit.spare.entities.StockOut;
import com.supconit.spare.entities.Transfer;


public interface SpareProcessService /*extends ProcessService*/{
    public void stockInProcessDone(String transitionId,StockIn stockIn);
    public void stockInProcessSave(StockIn stockIn);
    public void stockInProcessInstanceDelete(Long id);
    
    
    public void stockOutProcessDone(String transitionId,StockOut stockOut);
    public void stockOutProcessSave(StockOut stockOut);
    public void stockOutProcessInstanceDelete(Long id);
    
    public void transferProcessDone(String transitionId,Transfer transfer);
    public void transferProcessSave(Transfer transfer);
    public void transferProcessInstanceDelete(Long id);
    
    public void invertoryProcessDone(String transitionId,Inventory inventory);
    public void invertoryProcessSave(Inventory inventory);
    public void invertoryProcessInstanceDelete(Long id);
    public void stockBackProcessDone(String transitionId, StockBack task);
    

}
