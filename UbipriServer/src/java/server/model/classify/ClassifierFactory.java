/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.model.classify;

/**
 *
 * @author Borges
 */
public class ClassifierFactory {
    public static Classifier create(String name){
        Classifier result = null;
        switch(name){
            case "NNge":
                // Implementar
                break;
            case "RandomTree":
                // Implementar
            case "RandomTreeBorges":
                result = new ClassifierRandomTreeBorges();
                break;
            case "MultilayerPerceptron":
                // Implementar
                break;
            case "NotImplemented":
                result = new ClassifierNotImplemented();
                break;     
            default:
                result = null;
                break;
        }
        return result;
    }
    
    
}
