package main;

public class Main {
    
    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("  SISTEMA DE GESTION DE EMPRESAS");
        System.out.println("  TPI - Programacion 2");
        System.out.println("  UTN - Entre Rios");
        System.out.println("===========================================\n");
        
        AppMenu menu = new AppMenu();
        menu.mostrarMenu();
    }
}