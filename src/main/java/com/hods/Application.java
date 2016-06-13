/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hods;

/**
 *
 * @author maher
 */
public final class Application {
    private Application(){
        
    }
    private static class _C{
        static Database database = new FakeDatebase();
    }
    
    public static Database getDatabase(){
        return _C.database;
    }
}
