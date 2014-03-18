#include <stdio.h>
#include <stdlib.h>
#include "list.h"

/*Définition image et pixel*/
typedef struct t_pixel { int R,G,B,Z; } Pixel;

typedef struct t_image
{
  int width, height, maxColor;
  Pixel** pixel;
} Image;

typedef struct t_point
{
  int i,j,Z;
} Point;

/*****************************************************************************/

/*Création image*/
Image* new_image(int width, int height, int maxColor)
{
  if(width <= 0 || height <= 0) return 0;

  Image* img = malloc(sizeof(Image));
  img->width = width;
  img->height = height;
  img->maxColor = maxColor;
  img->pixel = malloc(width*sizeof(Pixel*));

  int i;
  for(i=0;i<width;i++)
  {
    img->pixel[i] = malloc(height*sizeof(Pixel));
    int j;
    for(j=0;j<height;j++) img->pixel[i][j].R = img->pixel[i][j].G = img->pixel[i][j].B = img->pixel[i][j].Z = 0;
  }

  puts("Creation image OK");

  return img;
}

/*****************************************************************************/

/*Destruction image*/
void delete_image(Image* img)
{
  if(img)
  {
    int i;
    for(i=0;i<img->width;i++) free(img->pixel[i]);
    free(img);
  }
}

/*****************************************************************************/

int max(int i1,int i2,int i3,int i4,int i5,int i6)
{
  int max = i1;
  if(i2 > max) max = i2;
  if(i3 > max) max = i3;
  if(i4 > max) max = i4;
  if(i5 > max) max = i5;
  if(i6 > max) max = i6;
  
  return max;
}

/*****************************************************************************/

int derivate_pixel(Image* img,int i,int j)
{
  int dxR,dxG,dxB,dyR,dyG,dyB;
  if(i != 0 && i != img->width-1)
  {
    dxR = img->pixel[i+1][j].R-img->pixel[i-1][j].R;
    dxG = img->pixel[i+1][j].G-img->pixel[i-1][j].G;
    dxB = img->pixel[i+1][j].B-img->pixel[i-1][j].B;
  }
  else if (i == 0)
  {
    dxR = img->pixel[i+1][j].R-img->pixel[i][j].R;
    dxG = img->pixel[i+1][j].G-img->pixel[i][j].G;
    dxB = img->pixel[i+1][j].B-img->pixel[i][j].B;
  }
  else
  {
    dxR = img->pixel[i][j].R-img->pixel[i-1][j].R;
    dxG = img->pixel[i][j].G-img->pixel[i-1][j].G;
    dxB = img->pixel[i][j].B-img->pixel[i-1][j].B;
  }
  dxR *= dxR; dxG *= dxG; dxB *= dxB;

  if(j != 0 && j != img->height-1)
  {
    dyR = img->pixel[i][j+1].R-img->pixel[i][j-1].R;
    dyG = img->pixel[i][j+1].G-img->pixel[i][j-1].G;
    dyB = img->pixel[i][j+1].B-img->pixel[i][j-1].B;
  }
  else if (j == 0)
  {
    dyR = img->pixel[i][j+1].R-img->pixel[i][j].R;
    dyG = img->pixel[i][j+1].G-img->pixel[i][j].G;
    dyB = img->pixel[i][j+1].B-img->pixel[i][j].B;
  }
  else
  {
    dyR = img->pixel[i][j].R-img->pixel[i][j-1].R;
    dyG = img->pixel[i][j].G-img->pixel[i][j-1].G;
    dyB = img->pixel[i][j].B-img->pixel[i][j-1].B;
  }
  dyR *= dyR; dyG *= dyG; dyB *= dyB;
  
  return max(dxR,dxG,dxB,dyR,dyG,dyB);
}

/*****************************************************************************/

/*int derivate_pixel(Image* img, int i, int j)
{
  int precXGRAY, suivXGRAY, precYGRAY, suivYGRAY, actGRAY,dxGRAY,dyGRAY;

  if(i != 0)             precXGRAY = (img->pixel[i-1][j].R + img->pixel[i-1][j].G + img->pixel[i-1][j].B)/3;
  if(i != img->width-1)  suivXGRAY = (img->pixel[i+1][j].R + img->pixel[i+1][j].G + img->pixel[i+1][j].B)/3;
  if(j != 0)             precYGRAY = (img->pixel[i][j-1].R + img->pixel[i][j-1].G + img->pixel[i][j-1].B)/3;
  if(j != img->height-1) suivYGRAY = (img->pixel[i][j+1].R + img->pixel[i][j+1].G + img->pixel[i][j+1].B)/3;
                         actGRAY = (img->pixel[i][j].R + img->pixel[i][j].G + img->pixel[i][j].B)/3;*/

  /*Calcul de l'approximation de la dérivée en x et en y*/
  /*if(i != 0 && i != img->width-1)  dxGRAY = (suivXGRAY-precXGRAY);
  else if (i == 0)                 dxGRAY = (suivXGRAY-actGRAY);
       else                        dxGRAY = (actGRAY-precXGRAY);

  if(j != 0 && j != img->height-1) dyGRAY = (suivYGRAY-precYGRAY);
  else if (j == 0)                 dyGRAY = (suivYGRAY-actGRAY);
       else                        dyGRAY = (actGRAY-precYGRAY);

  dxGRAY *= dxGRAY;
  dyGRAY *= dyGRAY;
  
  if( dxGRAY > dyGRAY) return dxGRAY;
  else return dyGRAY;
}*/

/*****************************************************************************/

/*Detection d'une forme en utilisant le niveau de gris*/
Image* detect_form(Image* img)
{
  int i,j,derivate_number,threshold;
  threshold = ((img->maxColor)*(img->maxColor))/2000;

  Image* img2 = new_image(img->width,img->height,img->maxColor);
  for(j=0;j<img->height;j++)
  {
    for(i=0;i<img->width;i++)
    {
      derivate_number = derivate_pixel(img,i,j);

      /*Coloration du pixel actuel si une des dérivées est trop grande*/
      if ( derivate_number > threshold )
      {
        img2->pixel[i][j].R = img->maxColor;
        img2->pixel[i][j].G = 0;
        img2->pixel[i][j].B = img->maxColor;
      }
      else
      {
        img2->pixel[i][j].R = img->pixel[i][j].R;
        img2->pixel[i][j].G = img->pixel[i][j].G;
        img2->pixel[i][j].B = img->pixel[i][j].B;
      }
    }
  }
  return img2;
}

/*****************************************************************************/

Image* flou(Image* img,int lvl)
{
  int i,j,m,n,sumR,sumG,sumB,denom;

  Image* img2 = new_image(img->width,img->height,img->maxColor);
  for(j=0;j<img->height;j++)
  {
    for(i=0;i<img->width;i++)
    {
      sumR=sumG=sumB=denom=0;

      for(m=i-lvl;m<i+lvl;m++)
      {
        if(m<0 || m>=img->width) continue;
        for(n=j-lvl;n<j+lvl;n++)
        {
          if(n<0 || n>=img->height) continue;
          sumR+=img->pixel[m][n].R;
          sumG+=img->pixel[m][n].G;
          sumB+=img->pixel[m][n].B;
          denom++;
        }
      }

      img2->pixel[i][j].R=sumR/denom;
      img2->pixel[i][j].G=sumG/denom;
      img2->pixel[i][j].B=sumB/denom;
    }
  }
  return img2;
}

/*****************************************************************************/

/*Lire un fichier image*/
Image* read_image_file(FILE* F)
{
  /*Lecture entête*/
  if(fgetc(F) != 'P') return 0;
  if(fgetc(F) != '3') return 0;
  if(fgetc(F) != '\n') return 0;

  /*Lecture commentaire*/
  char c = fgetc(F);
  while (c == '#')
  {
    c = fgetc(F);
    while(c != '\n') c = fgetc(F);
  }
  ungetc(c,F);

  puts("Lecture entete OK");

  /*Lecture des données image*/
  int width, height, maxColor;
  fscanf(F,"%d",&width);
  fscanf(F,"%d",&height);
  fscanf(F,"%d",&maxColor);

  Image* img = new_image(width,height,maxColor);

  if (img)
  {
    int i,j;
    for(j=0;j<height && !feof(F);j++)
    {
      for(i=0;i<width && !feof(F);i++)
      {
        fscanf(F,"%d",&(img->pixel[i][j].R));
        fscanf(F,"%d",&(img->pixel[i][j].G));
        fscanf(F,"%d",&(img->pixel[i][j].B));
      }
    }
  }

  return img;
}

/*****************************************************************************/

void write_image_file(FILE* F,Image* img)
{
  fputs("P3\n",F);
  fputs("# TEST DE DETECTION DES FORMES, A OUVRIR AVEC GIMP POUR COMPARAISON\n",F);
  fprintf(F,"%d %d\n%d\n",img->width,img->height,img->maxColor);

  int i,j;
  for(j=0;j<img->height;j++)
  {
    for(i=0;i<img->width;i++)
    {
      fprintf(F,"%d\n%d\n%d\n",img->pixel[i][j].R,img->pixel[i][j].G,img->pixel[i][j].B);
    }
  }
}

/*****************************************************************************/

void gray_level(Image* img)
{
  int i,j,avg;
  for(j=0;j<img->height;j++)
  {
    for(i=0;i<img->width;i++)
    {
      avg = (img->pixel[i][j].R + img->pixel[i][j].G + img->pixel[i][j].B)/3;
      img->pixel[i][j].R = avg;
      img->pixel[i][j].G = avg;
      img->pixel[i][j].B = avg;
    }
  }
}

/*****************************************************************************/

int compute_gray(Image* img)
{
  int i,j,avg = 0;
  for(j=0;j<img->height;j++)
  {
    for(i=0;i<img->width;i++)
    {
      avg += (img->pixel[i][j].R + img->pixel[i][j].G + img->pixel[i][j].B)/3;
    }
  }
  avg/=(img->height*img->width);
  return avg;
}

/*****************************************************************************/

void enlight_image(Image* img)
{
  int i,j;
  for(j=0;j<img->height;j++)
  {
    for(i=0;i<img->width;i++)
    {
      if(img->maxColor-30 < img->pixel[i][j].R) img->pixel[i][j].R = img->maxColor;
      else img->pixel[i][j].R += 30;
      if(img->maxColor-30 < img->pixel[i][j].G) img->pixel[i][j].G = img->maxColor;
      else img->pixel[i][j].G += 30;
      if(img->maxColor-30 < img->pixel[i][j].B) img->pixel[i][j].B = img->maxColor;
      else img->pixel[i][j].B += 30;
    }
  }
}

/*****************************************************************************/

int main()
{
  FILE *F1 = fopen("testDetection.ppm","r");
  if(F1 != 0)
  {
    Image* img1 = read_image_file(F1);
    /*enlight_image(img1);
    FILE *F5 = fopen("testDetectionClair.ppm","w");
    if(F5 != 0)
    {
      write_image_file(F5,img1);
      fclose(F5);
      puts("Ecriture OK");
    }
    fclose(F1);*/
    puts("Lecture OK");

    puts("Debut detection");
    //Image* img2 = flou(img1,5);
    Image* img3 = detect_form(img1);
    //gray_level(img1);
    //Image* img3 = detect_form(img1);
    puts("Detection OK");

    //FILE *F2 = fopen("testDetectionFlou.ppm","w");
    FILE *F3 = fopen("testDetection2.ppm","w");
    //FILE *F4 = fopen("testDetectionGris2.ppm","w");
    /*if(F2 != 0)
    {
      write_image_file(F2,img2);
      fclose(F2);
      puts("Ecriture OK");
    }*/
    if(F3 != 0)
    {
      write_image_file(F3,img3);
      fclose(F3);
      puts("Ecriture OK");
    }
    /*if(F4 != 0)
    {
      write_image_file(F4,img3);
      fclose(F4);
      puts("Ecriture OK");
    }
    FILE *F = fopen("Feuille.ppm","r");
    if(F!= 0)
    {
      Image* img = read_image_file(F);
      printf("Gris moyen de la feuille : %d\n",compute_gray(img));
      fclose(F);
    } */
  }

  return 0;
}