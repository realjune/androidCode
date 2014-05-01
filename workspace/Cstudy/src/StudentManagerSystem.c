/*
 ============================================================================
 Name        : Cstudy.c
 Author      : junxu
 Version     :
 Copyright   : Copyright 2014 by junxu
 Description : StudentManagerSystem in C, Ansi-style
 练习创建包含学生学号和姓名信息的单链表。其结构点数任意，以学号升序排列，以输入姓名为空结束。
 程序功能点：删除一个给定姓名的节点；插入一个给定学号和姓名的节点。
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>

/*链表节点的数据结构*/
struct node{
	int num;			/*学号*/
	char name[20];		/*姓名*/
	struct node *next;	/*指针域*/
};

/*主函数*/
main(){
	/*函数声明*/
	struct node *create();	/*创建链表*/
	struct node *insert();	/*插入节点*/
	struct node *delet();	/*删除节点*/
	void print();			/*输出链表*/
	struct node *head;		/**/
	char name[20];
	int n;

	head =NULL;				/*作空链表*/
	head=create(head);		/*调用函数创建以head为头的链表*/
	print(head);			/*调用函数输出节点*/

	printf("\nInput the inserted num and name:\n");
	gets(name);				/*输入学号*/
	n=atoi(name);			/**/
	gets(name);				/*输入姓名*/
	head=insert(head,name,n);	/*将结点插入链表*/
	print(head);

	printf("\nInput the deleted name:\n");
	gets(name);				/*输入被删除姓名*/
	head=delet(head,name);	/*调用函数删除结点*/
	print(head);
	return EXIT_SUCCESS;
}

/*创建链表*/
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

/*插入结点*/
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

/*删除结点*/
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

/*输出链表*/
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
