/* Copyright (C) 2007  Felipe A. Lessa
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 */
package tirateima;

/**
 * Um par de objetos.
 * @param A
 * @param B
 * @author felipe.lessa
 *
 */
public class Par<A, B> {
	/**
	 * Primeiro elemento do par.
	 */
	public A fst;
	
	/**
	 * Segundo elemento do par.
	 */
	public B snd;
	
	/**
	 * Cria um novo par.
	 * @param fst  primeiro elemento.
	 * @param snd  segundo elemento.
	 */
	public Par(A fst, B snd) {
		this.fst = fst;
		this.snd = snd;
	}
	
	@Override
	public String toString() {
		return "(" + fst.toString() + "," + snd.toString() + ")";
	}
}
