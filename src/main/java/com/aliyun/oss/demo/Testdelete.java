package com.aliyun.oss.demo;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.BucketInfo;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;

public class Testdelete {
	private static String endpoint="oss-cn-beijing.aliyuncs.com";
	private static String accessKeyId="LTAIfTVYN8w5mmi1";
	private static String accessKeySecret="fDLgyulAXElgA8IZXPuosidGbnvP4f";
	private static String bucketName="qwe-nie";
	public static void main(String[] args) {
		OSSClient ossClient=new OSSClient(endpoint, accessKeyId, accessKeySecret);
		if(ossClient.doesBucketExist(bucketName)) {
			System.out.println("�Ѵ��ڸô洢����BucketName��"+bucketName);
		}else {
			System.out.println("û�и�"+bucketName+"�洢���򣬴����ô洢����");
			ossClient.createBucket(bucketName);
		}
		listObject(ossClient);
		System.out.println("------ͼƬ�ϴ�--------");
		//ͼƬ�ϴ�
		upload(ossClient);
		listObject(ossClient);
		//���ϴ����ļ�ɾ��
		System.out.println("-------�ļ�ɾ��-------");
		delete(ossClient);
		System.out.println("--------------");
		
		listObject(ossClient);
		
		ossClient.shutdown();
		
		
	}
	private static void delete(OSSClient ossClient) {
		if(ossClient.doesObjectExist(bucketName, "*.jpg")) {
			System.out.println("�ô洢�ռ������� ���޷�����ɾ������");
		}else {
			ossClient.deleteObject(bucketName, "four.jpg");
			BucketInfo info=ossClient.getBucketInfo(bucketName);
			System.out.println("ɾ���ɹ�");
		}
	}
	/**
	 * �ϴ�ͼƬ�ļ�
	 * @param ossClient
	 */
	private static void upload(OSSClient ossClient) {
		final  String fileKey1="three.jpg";
		ossClient.putObject( bucketName,fileKey1, new File("C:\\Users\\pc\\Desktop\\����ͼƬ\\netpay.jpg"));
		final  String fileKey2="four.jpg";
		ossClient.putObject( bucketName,fileKey2, new File("C:\\Users\\pc\\Desktop\\����ͼƬ\\mobilepay.jpg"));
			
		}
	private static void listObject(OSSClient ossClient) {
		ObjectListing list = ossClient.listObjects(bucketName);
		List<OSSObjectSummary> sum = list.getObjectSummaries();
		for (OSSObjectSummary object : sum) {
			System.out.println(object.getKey());
		}
	}
	
}
