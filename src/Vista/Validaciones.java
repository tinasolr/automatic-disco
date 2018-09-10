/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;
import java.util.Scanner;
/**
 *
 * @author Nico
 */
public class Validaciones {
 public Validaciones(){}
	
	public static int ValidacionInt(Scanner teclado)
	{
		int num =-1;
		do
		{
			while (!teclado.hasNextInt())
			{
				teclado.nextLine();
				System.out.println("Ingreso Erroneo. Ingrese nuevamente el valor");
			}
			num = teclado.nextInt();
		}
		while(num < 0 );
		return num;
	}
	
	public static float ValidacionFlotante(Scanner teclado)
	{
		float num =-1;
		do
		{
			while (!teclado.hasNextFloat())
			{
				teclado.nextLine();
				System.out.println("Ingreso Erroneo. Ingrese nuevamente el valor");
			}
			num = teclado.nextFloat();
		}
		while(num < 0 );
		return num;
	}
	
	public static double ValidacionDouble(Scanner teclado)
	{
		double num =-1;
		do
		{
			while (!teclado.hasNextDouble())
			{
				teclado.nextLine();
				System.out.println("Ingreso Erroneo. Ingrese nuevamente el valor");
			}
			num = teclado.nextFloat();
		}
		while(num < 0 );
		return num;
	}
	
	public static boolean ValidacionBolean(Scanner teclado)
	{
		int num =-1;
		do
		{
			while (!teclado.hasNextInt())
			{
				teclado.nextLine();
				System.out.println("Ingreso Erroneo. Ingrese nuevamente el valor");
			}
			num = teclado.nextInt();
		}
		while(num < 0 || num >1 );
		if (num ==0) return false;
		else return true;
	}
	
	public static String ValidacionCadena(Scanner teclado)
	{
		String cadena= "";
		cadena = teclado.next();
		return cadena;
	}
	
	
	public static String validarFecha(){
		Scanner input = new Scanner (System.in);
		boolean esFecha = false;
		String fecha = null;
		String patron = "((0[0-9]|[0-9])|1[0-9]|2[0-9]|3[0-1])(/)((0[0-9]|[0-9])|1[0-2])(/)([0-9]{4})";
		while(!esFecha){
			fecha = input.nextLine();
			if(!fecha.matches(patron))
				System.out.print("Por favor, siga el patrón dd/mm/aaaa ");
			else 
				esFecha = true;
		}
		return fecha;
	}
	
	public static boolean VoF(){
		
		Scanner input = new Scanner (System.in);
		boolean vf = false;
		char b = 0;
	
		while(b!='S' && b!='s' && b!='N' && b!='n')
		{
			b = input.next().charAt(0);
			if(b != 0)
			{
				if(b == 'S' || b == 's')
					vf = true;
				else if(b == 'N' || b == 'n')
					vf = false;
				else
					System.out.print("Ingrese S o N: ");
			}
			else
				System.out.print("El dato debe ser S o N. Ingrese S o N: ");
		}
		return vf;
	}   
}
