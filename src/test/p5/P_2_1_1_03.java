package test.p5;

import processing.core.*; 
import processing.data.*; 
import processing.opengl.*; 

import processing.pdf.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class P_2_1_1_03 extends PApplet {

// P_2_1_1_03.pde
// 
// Generative Gestaltung, ISBN: 978-3-87439-759-9
// First Edition, Hermann Schmidt, Mainz, 2009
// Hartmut Bohnacker, Benedikt Gross, Julia Laub, Claudius Lazzeroni
// Copyright 2009 Hartmut Bohnacker, Benedikt Gross, Julia Laub, Claudius Lazzeroni
//
// http://www.generative-gestaltung.de
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * changing number, color and strokeweight on diagonals in a grid
 * 	 
 * MOUSE
 * position x          : diagonal strokeweight
 * position y          : number diagonals
 * left click          : new random layout
 * 
 * KEYS
 * s                   : save png
 * p                   : save pdf
 * 1                   : color left diagonal
 * 2                   : color right diagonal
 * 3                   : switch transparency left diagonal on/off
 * 4                   : switch transparency right diagonal on/off
 * 0                   : default
 */


boolean savePDF = false;

int colorBack = color(255);
int colorLeft = color(0);
int colorRight = color(0);

float tileCount = 1;
boolean transparentLeft = false;
boolean transparentRight = false;
float alphaLeft = 100;
float alphaRight = 100;

int actRandomSeed = 0;


public void setup() {
  size(600, 600);

  colorMode(HSB, 360, 100, 100, 100);
  colorLeft = color(323, 100, 77);
}


public void draw() {
  if (savePDF) beginRecord(PDF, timestamp()+".pdf");

  colorMode(HSB, 360, 100, 100, 100);
  background(colorBack);
  smooth();
  noFill();
  randomSeed(actRandomSeed);
  strokeWeight(mouseX/15);

  tileCount = mouseY/15;

  for (int gridY=0; gridY<tileCount; gridY++) {
    for (int gridX=0; gridX<tileCount; gridX++) {

      float posX = width/tileCount*gridX;
      float posY = height/tileCount*gridY;

      if (transparentLeft == true) alphaLeft = gridY*10; 
      else alphaLeft = 100;

      if (transparentRight == true) alphaRight = 100-gridY*10; 
      else alphaRight = 100;

      int toggle = (int) random(0,2);

      if (toggle == 0) {
        stroke(colorLeft, alphaLeft);
        line(posX, posY, posX+(width/tileCount)/2, posY+height/tileCount);
        line(posX+(width/tileCount)/2, posY, posX+(width/tileCount), posY+height/tileCount);
      }
      if (toggle == 1) {
        stroke(colorRight, alphaRight);
        line(posX, posY+width/tileCount, posX+(height/tileCount)/2, posY);
        line(posX+(height/tileCount)/2, posY+width/tileCount, posX+(height/tileCount), posY);
      }
    }
  }

  if (savePDF) {
    savePDF = false;
    endRecord();
  }
}


public void mousePressed() {
  actRandomSeed = (int) random(100000);
}


public void keyReleased(){
  if (key == 's' || key == 'S') saveFrame(timestamp()+"_##.png");
  if (key == 'p' || key == 'P') savePDF = true;

  if (key == '1'){
    if (colorLeft == color(273, 73, 51)) {
      colorLeft = color(323, 100, 77);
    } 
    else {
      colorLeft = color(273, 73, 51);
      //      colorLeft = color(0);
    } 
  }
  if (key == '2'){
    if (colorRight == color(0)) {
      colorRight = color(192, 100, 64);
    } 
    else {
      colorRight = color(0);
    } 
  }
  if (key == '3'){
    transparentLeft =! transparentLeft;
  }
  if (key == '4'){
    transparentRight =! transparentRight;
  }

  if (key == '0'){
    transparentLeft = false;
    transparentRight = false;
      colorLeft = color(323, 100, 77);
      colorRight = color(0);
  }
}


// timestamp
public String timestamp() {
  Calendar now = Calendar.getInstance();
  return String.format("%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS", now);
}











  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "P_2_1_1_03" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
