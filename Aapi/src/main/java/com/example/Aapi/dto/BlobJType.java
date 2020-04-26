package com.example.Aapi.dto;

/**
 * BlobJ type enumerator. BlobJ can only have one of the value.
 * @author Aymeric NEUMANN
 *
 */
public enum BlobJType {
	
	//Contains a 'a' in his name
	TYPE_A,
	
	//Contains a 'b' in his name or have a name with a even letter count number
	TYPE_B,
	
	//Doesn't contains 'a' or 'b' in his name and an odd count letter number
	TYPE_C,
	
	//Extra Blob
	TYPE_X;
}
