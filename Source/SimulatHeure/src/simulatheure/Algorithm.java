/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatheure;

/**
 *
 * @author Rémi
 */
public interface Algorithm {
        
   public static double triangular(double a,double b,double c) {
        double U = Math.random();
        double F = (c - a) / (b - a);
        if (U <= F)
           return a + Math.sqrt(U * (b - a) * (c - a));
        else
           return b - Math.sqrt((1 - U) * (b - a) * (b - c));
    }
}
