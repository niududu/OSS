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
	private static String accessKeyId="";
	private static String accessKeySecret="";
	private static String bucketName="qwe-nie";
	public static void main(String[] args) {
		OSSClient ossClient=new OSSClient(endpoint, accessKeyId, accessKeySecret);
		if(ossClient.doesBucketExist(bucketName)) {
			System.out.println("已存在该存储区域，BucketName："+bucketName);
		}else {
			System.out.println("没有该"+bucketName+"存储区域，创建该存储区域。");
			ossClient.createBucket(bucketName);
		}
		listObject(ossClient);
		System.out.println("------图片上传--------");
		//图片上传
		upload(ossClient);
		listObject(ossClient);
		//将上传的文件删除
		System.out.println("-------文件删除-------");
		delete(ossClient);
		System.out.println("--------------");
		
		listObject(ossClient);
		
		ossClient.shutdown();
		
		
	}
	private static void delete(OSSClient ossClient) {
		if(ossClient.doesObjectExist(bucketName, "*.jpg")) {
			System.out.println("该存储空间无内容 ，无法进行删除操作");
		}else {
			ossClient.deleteObject(bucketName, "four.jpg");
			BucketInfo info=ossClient.getBucketInfo(bucketName);
			System.out.println("删除成功");
		}
	}
	/**
	 * 上传图片文件
	 * @param ossClient
	 */
	private static void upload(OSSClient ossClient) {
		final  String fileKey1="three.jpg";
		ossClient.putObject( bucketName,fileKey1, new File("C:\\Users\\pc\\Desktop\\官网图片\\netpay.jpg"));
		final  String fileKey2="four.jpg";
		ossClient.putObject( bucketName,fileKey2, new File("C:\\Users\\pc\\Desktop\\官网图片\\mobilepay.jpg"));
			
		}
	private static void listObject(OSSClient ossClient) {
		ObjectListing list = ossClient.listObjects(bucketName);
		List<OSSObjectSummary> sum = list.getObjectSummaries();
		for (OSSObjectSummary object : sum) {
			System.out.println(object.getKey());
		}
	}
	
}
