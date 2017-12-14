package com.aliyun.oss.demo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.BucketInfo;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;

/*
	 * OSS�Ĵ����ԣ�
	 * endpoint ��OSS����
	 * accessKsyID������ID
	 * accessKeySecret:������Կ
	 * BucketName:Bucket����������Object�Ĵ洢�ռ�
	 */
public class OssTest {
	private static String endpoint="https://oss-cn-shenzhen.aliyuncs.com";
	private static String accessKeyId="LTAIfTVYN8w5mmi1";
	private static String accessKeySecret="fDLgyulAXElgA8IZXPuosidGbnvP4f";
	private static String bucketName="niududu-new";
	
	public static void main(String[] args) {
		//����OSSClient����
		OSSClient ossClient=new OSSClient(endpoint, accessKeyId, accessKeySecret);
		//��׳���ж��Ƿ���ڸ�Bucket����
		if(ossClient.doesBucketExist(bucketName)) {
			System.out.println("�ô洢�ռ�"+bucketName+"�Ѵ���");
		}else {
			System.out.println("�ô洢�ռ�"+bucketName+"�����ڣ����ڴ�����");
			ossClient.createBucket(bucketName);
		}
		BucketInfo bucketInfo = ossClient.getBucketInfo(bucketName);
		System.out.println("Bucket��"+bucketName+"��Ϣ����");
		System.out.println(bucketInfo.getBucket().getCreationDate());
		System.out.println(bucketInfo.getBucket().getName());
		System.out.println(bucketInfo.getBucket().getLocation());
		System.out.println(bucketInfo.getBucket().toString());
		
		//�ϴ�ͼƬ�ļ���������
		String  fileKey ="mobliePay1.jpg";
//		final String keySuffixWithSlash="parent_directory/";
//		ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
		//�ӱ����ļ��ϴ�new File("�����ļ�·��")��
		ossClient.putObject(bucketName,fileKey, new File("C://Users//pc//Desktop//����ͼƬ/132.jpg"));
		 System.out.println("Object��" + fileKey + "����OSS�ɹ���");
		 
		 ObjectListing objectListing = ossClient.listObjects(bucketName);
         List<OSSObjectSummary> objectSummary = objectListing.getObjectSummaries();
         System.out.println("��������Object��");
         for (OSSObjectSummary object : objectSummary) {
             System.out.println("\t" + object.getKey());
         }
         ossClient.deleteBucket("<bucketName>");
         ossClient.shutdown();
	}
	

}
