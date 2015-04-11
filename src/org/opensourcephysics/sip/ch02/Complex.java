/*
 * Open Source Physics software is free software as described near the bottom of this code file.
 *
 * For additional information and documentation on Open Source Physics please see: 
 * <http://www.opensourcephysics.org/>
 */

package org.opensourcephysics.sip.ch02;

/**
 * Complex defines a new data type that models a complex number.
 *
 * @author Wolfgang Christian, Jan Tobochnik, Harvey Gould, Joshua Gould
 * @version 1.0 05/07/05
 */
public class Complex {
  private double real = 0;
  private double imag = 0; // real, imag are instance variables

  /**
   * Constructs a complex number.
   */
  public Complex() {
    this(0, 0); // invokes second constructor with 0 + i0
  }

  /**
   * Constructs a complex number with given real and imaginary parts.
   *
   * @param real double
   * @param imag double
   */
  public Complex(double real, double imag) {
    this.real = real;
    this.imag = imag;
  }

  /**
   * Conjugates this complex number.
   * The current complex number is changed.
   */
  public void conjugate() {
    imag = -imag;
  }

  /**
   * Adds a complex number to this complex number and returns the new complex number.
   * The current complex number is not changed.
   *
   * @param c Complex
   * @return Complex
   */
  public Complex add(Complex c) {
    // result also is complex so need to introduce another variable of type Complex
    Complex sum = new Complex();
    sum.real = real+c.real;
    sum.imag = imag+c.imag;
    return sum;
  }

  /**
   * Multiples this complex number by another complex number and returns the new complex number.
   * The current complex number is not changed.
   *
   * @param c Complex
   * @return Complex
   */
  public Complex multiply(Complex c) {
    Complex product = new Complex();
    product.real = (real*c.real)-(imag*c.imag);
    product.imag = (real*c.imag)+(imag*c.real);
    return product;
  }

  /**
   * Represents this complex number as a string.
   *
   * @return String
   */
  public String toString() {
    // note example of method overriding
    if(imag>=0) {
      return real+" + i"+Math.abs(imag);
    }
	return real+" - i"+Math.abs(imag);
  }
}

/* 
 * Open Source Physics software is free software; you can redistribute
 * it and/or modify it under the terms of the GNU General Public License (GPL) as
 * published by the Free Software Foundation; either version 2 of the License,
 * or(at your option) any later version.

 * Code that uses any portion of the code in the org.opensourcephysics package
 * or any subpackage (subdirectory) of this package must must also be be released
 * under the GNU GPL license.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston MA 02111-1307 USA
 * or view the license online at http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2007  The Open Source Physics project
 *                     http://www.opensourcephysics.org
 */
