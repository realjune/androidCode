/*
 ============================================================================
 Name        : Cstudy.c
 Author      : junxu
 Version     :
 Copyright   : Copyright 2014 by junxu
 Description : StudentManagerSystem in C, Ansi-style
 ��ϰ��������ѧ��ѧ�ź�������Ϣ�ĵ�������ṹ�������⣬��ѧ���������У�����������Ϊ�ս�����
 �����ܵ㣺ɾ��һ�����������Ľڵ㣻����һ������ѧ�ź������Ľڵ㡣
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>

/*����ڵ�����ݽṹ*/
struct node{
	int num;			/*ѧ��*/
	char name[20];		/*����*/
	struct node *next;	/*ָ����*/
};

/*������*/
main(){
	/*��������*/
	struct node *create();	/*��������*/
	struct node *insert();	/*����ڵ�*/
	struct node *delet();	/*ɾ���ڵ�*/
	void print();			/*�������*/
	struct node *head;		/**/
	char name[20];
	int n;

	head =NULL;				/*��������*/
	head=create(head);		/*���ú���������headΪͷ������*/
	print(head);			/*���ú�������ڵ�*/

	printf("\nInput the inserted num and name:\n");
	gets(name);				/*����ѧ��*/
	n=atoi(name);			/**/
	gets(name);				/*��������*/
	head=insert(head,name,n);	/*������������*/
	print(head);

	printf("\nInput the deleted name:\n");
	gets(name);				/*���뱻ɾ������*/
	head=delet(head,name);	/*���ú���ɾ�����*/
	print(head);
	return EXIT_SUCCESS;
}

/*��������*/
struct node *create(struct node *head){
	char temp[30];
	struct node *p1,*p2;

	p1=p2=(struct node*)malloc(sizeof(struct node));
	printf("Input the num and name:\n");
	printf("Exit:double times Enter!\n");
	gets(temp);
	gets(p1->name);
	p1->num=atoi(temp);
	p1->next=NULL;
	while(strlen(p1->name)>0){
		if(head==NULL) head=p1;
		else p2->next=p1;
		p2=p1;
		p1=(struct node *)malloc(sizeof(struct node));
		printf("Input the num and name:\n");
		printf("Exit:double times Enter!\n");
		gets(temp);
		gets(p1->name);
		p1->num=atoi(temp);
		p1->next=NULL;
	}
	return head;
}

/*������*/
struct node *insert(struct node *head,char *name,int n){
	struct node *p1,*p2,*p3;
	p1=(struct node *)malloc(sizeof(struct node));
	strcpy(p1->name,name);
	p1->num=n;
	p2=head;
	if(head==NULL){
		head=p1;p1->next=NULL;
	}else{
		while(n>p2->num&&p2->next!=NULL){
			p3=p2;
			p2=p2->next;
		}
		if(n<p2->num){
			if(head==p2){
				head=p1;
				p1->next=p2;
			}else{
				p3->next=p1;
				p1->next=p2;
			}
		}else{
				p2->next=p1;
				p1->next=NULL;
			}
	}
	return (head);
}

/*ɾ�����*/
struct node *delet(struct node *head,char *name){
	struct node *temp,*p;

	temp=head;
	if(temp==NULL){
		printf("\nList is null!\n");
	}else {
		while(strcmp(temp->name,name)!=0&&temp->next!=NULL){
			p=temp;
			temp=temp->next;
		}
		if(strcmp(temp->name,name)==0){
			if(temp==head){
				head=head->next;
				printf("Delete the string(head):%s\n",temp->name);
				free(temp);
			}else{
				p->next=temp->next;
				printf("Delete the string:%s\n",temp->name);
				free(temp);
			}
		}else{
			printf("No find string!\n");
		}
	}
		return (head);
}

/*�������*/
void print(struct node *head){
	struct node *temp;
	temp=head;
	printf("\nOutput strings:\n");
	while(temp!=NULL){
		printf("\n%d------%s\n",temp->num,temp->name);
		temp=temp->next;
	}
	return;
}
