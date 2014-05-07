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
struct node {
	int num; /*ѧ��*/
	char name[20]; /*����*/
	struct node *next; /*ָ����*/
};

typedef struct node STUDENT;

/*������*/
int mainStudentManagerSystem() {
	setbuf(stdout, NULL); /*ȡ����Ĭ�����������buffer����*/
	/*��������*/
	char menu(); /*�˵�*/
	struct node *create(); /*��������*/
	struct node *insert(); /*����ڵ�*/
	struct node *delet(); /*ɾ���ڵ�*/
	void print(); /*�������*/
	STUDENT *find();

	/*���ݶ���*/
	struct node *head; /**/
	char name[20];
	int n;
	head = NULL; /*��������*/

	char type=0;
	while ((type=menu())!='q') {
		switch (type) {
		case '0':
			return EXIT_SUCCESS;
		case '1':
			head = create(head); /*���ú���������headΪͷ������*/
			break;
		case '2':
			printf("\nInput the inserted num and name:\n");
			gets(name); /*����ѧ��*/
			n = atoi(name); /**/
			gets(name); /*��������*/
			head = insert(head, name, n); /*������������*/
			break;
		case '3':
			printf("\nInput the deleted name:\n");
			gets(name); /*���뱻ɾ������*/
			head = delet(head, name); /*���ú���ɾ�����*/
			break;
		case '4':
			print(head); /*���ú�������ڵ�*/
			printf("press any key back...");
			scanf("%c", &type);
			scanf("%c", &type);
			break;
		case '5':
			printf("\nInput the find name:\n");
			gets(name);
			STUDENT *stu=find(head,name);
			break;
		}
	}
//	head=create(head);		/*���ú���������headΪͷ������*/
//	print(head);			/*���ú�������ڵ�*/
//
//	printf("\nInput the inserted num and name:\n");
//	gets(name);				/*����ѧ��*/
//	n=atoi(name);			/**/
//	gets(name);				/*��������*/
//	head=insert(head,name,n);	/*������������*/
//	print(head);
//
//	printf("\nInput the deleted name:\n");
//	gets(name);				/*���뱻ɾ������*/
//	head=delet(head,name);	/*���ú���ɾ�����*/
//	print(head);
	return EXIT_SUCCESS;
}

/*�˵�*/
char menu() {
	char type[10];
	printf("Student Manager System demo\n");
	printf("create node(1)\n");
	printf("insert node(2)\n");
	printf("delet node(3)\n");
	printf("print nod(4)\n");
	printf("find node(5)\n");
	printf("exit(q)\n");
//	scanf("%c", &type);
	gets(type);
	return type[0];
}
/*��������*/
struct node *create(struct node *head) {
	char temp[30];
	struct node *newNode, *curNode;

	newNode = curNode = (STUDENT*) malloc(sizeof(STUDENT));
	printf("Input the num and name:\n");
	printf("Exit:double times Enter!\n");
	gets(temp);
	newNode->num = atoi(temp);
	printf("num:%d\n",newNode->num);
	gets(newNode->name);
	printf("%s\n",newNode->name);
	newNode->next = NULL;
	while (strlen(newNode->name) > 0) {
		if (head == NULL)
			head = newNode;
		else
			curNode->next = newNode;
		curNode = newNode;
		newNode = (struct node *) malloc(sizeof(struct node));
		printf("Input the num and name:\n");
		printf("Exit:double times Enter!\n");
			gets(temp);
			newNode->num = atoi(temp);
//			scanf("%d",&(newNode->num));
			printf("num:%d\n",newNode->num);
			gets(newNode->name);
			printf("%s\n",newNode->name);
			newNode->next = NULL;
	}
	return head;
}

/*������*/
struct node *insert(struct node *head, char *name, int n) {
	struct node *newNode, *curentNode, *lastNode;
	newNode = (struct node *) malloc(sizeof(struct node));
	strcpy(newNode->name, name);
	newNode->num = n;
	curentNode = head;
	if (head == NULL) {
		head = newNode;
		newNode->next = NULL;
	} else {
		while (n > curentNode->num && curentNode->next != NULL) {
			lastNode = curentNode;
			curentNode = curentNode->next;
		}
		if (n < curentNode->num) {
			if (head == curentNode) { /*�����ͷ*/
				head = newNode;
				newNode->next = curentNode;
			} else { /*�����м�*/
				lastNode->next = newNode;
				newNode->next = curentNode;
			}
		} else { /*�����β*/
			curentNode->next = newNode;
			newNode->next = NULL;
		}
	}
	return (head);
}

/*ɾ�����*/
struct node *delet(struct node *head, char *name) {
	struct node *temp, *p;

	temp = head;
	if (temp == NULL) {
		printf("\nList is null!\n");
	} else {
		while (strcmp(temp->name, name) != 0 && temp->next != NULL) {/*����*/
			p = temp;
			temp = temp->next;
		}
		if (strcmp(temp->name, name) == 0) {/*���ҵ�*/
			if (temp == head) {/*��ͷ*/
				head = head->next;
				printf("Delete the string(head):%s\n", temp->name);
				free(temp);
			} else {/*���л��β*/
				p->next = temp->next;
				printf("Delete the string:%s\n", temp->name);
				free(temp);
			}
		} else {
			printf("No find string!\n");
		}
	}
	return (head);
}

STUDENT *find(STUDENT *head,char *name){
	STUDENT *temp;
	temp=head;
	if(temp==NULL){
		printf("\nList is null\n");
		return NULL;
	}else{
		while(strcmp(temp->name,name)!=0&&temp->next!=NULL){
			temp=temp->next;
		}
		if(strcmp(temp->name,name)==0){
			printf("\nFind num:%d\n",temp->num);
			return temp;
		}
	}
	return NULL;
}

/*�������*/
void print(struct node *head) {
	struct node *temp;
	temp = head;
	printf("\nOutput strings:\n");
	while (temp != NULL) {
		printf("\n%d------%s\n", temp->num, temp->name);
		temp = temp->next;
	}
	return;
}
