package com.encrypty;

import java.io.Serializable;
import java.util.List;

import Jama.Matrix;

public class SecretKey implements Serializable {

	private static final long serialVersionUID = -7613392355071806584L;
	
	private Matrix M1,M2;      //加密矩阵
	private Matrix M1_Inverse,M2_Inverse;      //加密逆矩阵
	public Matrix getM1_Inverse() {
		return M1_Inverse;
	}
	public void setM1_Inverse(Matrix m1_Inverse) {
		M1_Inverse = m1_Inverse;
	}
	public Matrix getM2_Inverse() {
		return M2_Inverse;
	}
	public void setM2_Inverse(Matrix m2_Inverse) {
		M2_Inverse = m2_Inverse;
	}
	private List<Integer> L;   //向量填充依据
	private List<Integer> R;   //向量拆分依据
	private int S;             //向量size
	
	public Matrix getM1() {
		return M1;
	}
	public void setM1(Matrix m1) {
		M1 = m1;
	}
	public Matrix getM2() {
		return M2;
	}
	public void setM2(Matrix m2) {
		M2 = m2;
	}
	public List<Integer> getL() {
		return L;
	}
	public void setL(List<Integer> l) {
		L = l;
	}
	public int getS() {
		return S;
	}
	public void setS(int s) {
		S = s;
	}
	public List<Integer> getR() {
		return R;
	}
	public void setR(List<Integer> r) {
		R = r;
	}
}
