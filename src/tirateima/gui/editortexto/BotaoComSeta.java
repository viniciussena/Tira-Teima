package tirateima.gui.editortexto;

/*
 * BotaoComSeta.java
 *
 * Created on 11 de Abril de 2007
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

/**
 * Modela um botao com uma seta
 *
 * @version
 * @author Luciano Santos
 */
@SuppressWarnings("serial")
public class BotaoComSeta extends javax.swing.JButton{
    
	/*
	 * Constantes para direcao da seta.
	 * 
	 */
	public final int BTN_ESQ = 1; //esquerda
    public final int BTN_DIR = 2; //direita
    public final int BTN_CIM = 3; //cima
    public final int BTN_BAI = 4; //baixo
    
    /*
     * Guarda a direcao da seta como um inteiro.
     */
    private int direcao;
    
    /*
     * Cores para o fundo do botao e para a seta.
     */
    private Color fundo, seta;
        
    /**
     * Cria novo botao com seta. Padrao: seta para esquerda.
     *
     *@param direcao Texto descrevendo a direcao. Pode ser "Esquerda", "Direita",
     * "Cima" ou "Baixo".
     *
     *@param fundo Cor do fundo do botao.
     *@param seta Cor da seta.
     */
    public BotaoComSeta(String direcao, Color fundo, Color seta) {
        this.direcao = obterDirecao(direcao);
        this.fundo = fundo;
        this.seta = seta;
    }
    
    /**
     * Cria novo botao. Padrao: seta para esquerda.
     *
     *@param direcao Identificador para a direcao. Deve ser uma
     *das constantes de direcao validas: BNT_ESQ, BTN_DIR, BTN_CIMA,
     *BTN_BAI. Se informado outro valor, sera assumido BTN_ESQ.
     *
     *@param fundo Cor do fundo do botao.
     *@param seta Cor da seta.
     */
    public BotaoComSeta(int direcao, Color fundo, Color seta) {
        this.direcao = obterDirecao(direcao);
        this.fundo = fundo;
        this.seta = seta;
    }
    
    /**
     * Altera cor de fundo do botao.
     *
     *@param fundo Nova cor do fundo do botao.
     */
    public void setFundo(Color fundo){
        this.fundo = fundo;
    }
    
    /**
     * Altera cor da seta.
     *
     *@param seta Nova cor da seta.
     */
    public void setSeta(Color seta){
        this.seta = seta;
    }
    
    /**
     * Altera direcao da seta.
     *
     *@param direcao Identificador para a direcao. Deve ser uma
     *das constantes de direcao validas: BNT_ESQ, BTN_DIR, BTN_CIMA,
     *BTN_BAI. Se informado outro valor, sera assumido BTN_ESQ.
     */
    public void setDirecao(int direcao){
    	this.direcao = obterDirecao(direcao);
    }
    
    /**
     * Altera direcao da seta.
     *
     *@param direcao Texto que descreve a direcao, deve ser "Esquerda", "Direita",
     *"Cima" ou "Baixo". Se nao for informado um valor valido, sera assumido "Esquerda".
     */
    public void setDirecao(String direcao){
    	this.direcao = obterDirecao(direcao);
    }
    
    /**
     * Reescreve o metodo de desenho do botao.
     *
     *@param g Componente grafico do botao.
     */
    public void paintComponent(Graphics g){
    	/*
    	 *Pinta o fundo.
    	 */
    	setBackground(fundo);
    	super.paintComponent(g);
    	
    	/*
    	 * Calcula pontos da seta e a desenha na tela.
    	 * 
    	 * Obs.: a seta sempre tem 1/3 da largura e 2/3 da altura.
    	 */
    	Rectangle r = getBounds();
    	r = (Rectangle) r.clone();
    	
    	/*
    	 * Pontos para desenhar a seta.
    	 */
    	Point A, B, C;
    	A = new Point();
    	B = new Point();
    	C = new Point();
    	
    	if((direcao == BTN_DIR)||(direcao == BTN_ESQ)){
    		A.y = r.height/6;
        	B.y = 5*r.height/6;
        	C.y = r.height/2;
    		if(direcao == BTN_DIR){
				B.x = A.x = r.width/3;
				C.x = 2*r.width/3;
    		}else{
    			B.x = A.x = 2*r.width/3;
				C.x = r.width/3;
    		}
    	}else{
    		A.x = r.width/3;
    		B.x = 2*r.width/3;
    		C.x = r.width/2;
    		if(direcao == BTN_CIM){
    			B.y = A.y = 5*r.height/6;
				C.y = r.height/6;
    		}else{
    			B.y = A.y = r.height/6;
				C.y = 5*r.height/6;
    		}
    	}
    	
    	Polygon p = new Polygon();
    	p.addPoint(A.x, A.y);
    	p.addPoint(B.x, B.y);
    	p.addPoint(C.x, C.y);
    	
    	g.setColor(seta);
    	g.fillPolygon(p);
    }
    
    /**
     * Retorna a constante de direcao correta, a partir de uma string
     * informada. Se for passada uma uma opcao correta, retorna-a, se for
     * passada uma opcao incorreta, retornara BTN_ESQ.
     * 
     * @param direcao Texto que descreve a direcao.
     * @return Constante valida de direcao.
     */
    protected int obterDirecao(String direcao){
    	if(direcao.equalsIgnoreCase("Direita"))
            return BTN_DIR;
        else if(direcao.equalsIgnoreCase("Cima"))
            return BTN_CIM;
        else if(direcao.equalsIgnoreCase("Baixo"))
            return BTN_BAI;
        else
            return BTN_ESQ;
    }
    
    /**
     * Retorna a constante de direcao correta, a partir de um valor
     * informado. Se for passada uma uma opcao correta, retorna-a, se for
     * passada uma opcao incorreta, retornara BTN_ESQ.
     * 
     * @param direcao Um inteiro qualquer.
     * @return Constante valida de direcao.
     */
    protected int obterDirecao(int direcao){
    	switch(direcao){
	        case BTN_DIR:
	        case BTN_CIM:
	        case BTN_BAI:
	            return direcao;
	        default:
	            return BTN_ESQ;
    	}
    }
}
