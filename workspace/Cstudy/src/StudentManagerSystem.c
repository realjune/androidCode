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
struct student {
	int num; /*ѧ��*/
	char name[20]; /*����*/
	struct student *next; /*ָ����*/
};

typedef struct student STUDENT;

/*������*/
int StudentManagerSystemMain() {
	setbuf(stdout, NULL); /*ȡ����Ĭ�����������buffer����*/
	/*��������*/
	char menu(); /*�˵�*/
	struct student *create(); /*��������*/
	struct student *insert(); /*����ڵ�*/
	struct student *delet(); /*ɾ���ڵ�*/
	void printStudent(); /*�������*/
	STUDENT *find();

	/*���ݶ���*/
	struct student *head; /**/
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
			printStudent(head); /*���ú�������ڵ�*/
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
	printf("create student(1)\n");
	printf("insert student(2)\n");
	printf("delet student(3)\n");
	printf("print nod(4)\n");
	printf("find student(5)\n");
	printf("exit(q)\n");
//	scanf("%c", &type);
	gets(type);
	return type[0];
}
/*��������*/
struct student *create(struct student *head) {
	char temp[30];
	struct student *newstudent, *curstudent;

	newstudent = curstudent = (STUDENT*) malloc(sizeof(STUDENT));
	printf("Input the num and name:\n");
	printf("Exit:double times Enter!\n");
	gets(temp);
	newstudent->num = atoi(temp);
	printf("num:%d\n",newstudent->num);
	gets(newstudent->name);
	printf("%s\n",newstudent->name);
	newstudent->next = NULL;
	while (strlen(newstudent->name) > 0) {
		if (head == NULL)
			head = newstudent;
		else
			curstudent->next = newstudent;
		curstudent = newstudent;
		newstudent = (struct student *) malloc(sizeof(struct student));
		printf("Input the num and name:\n");
		printf("Exit:double times Enter!\n");
			gets(temp);
			newstudent->num = atoi(temp);
//			scanf("%d",&(newstudent->num));
			printf("num:%d\n",newstudent->num);
			gets(newstudent->name);
			printf("%s\n",newstudent->name);
			newstudent->next = NULL;
	}
	return head;
}

/*������*/
struct student *insert(struct student *head, char *name, int n) {
	struct student *newstudent, *curentstudent, *laststudent;
	newstudent = (struct student *) malloc(sizeof(struct student));
	strcpy(newstudent->name, name);
	newstudent->num = n;
	curentstudent = head;
	if (head == NULL) {
		head = newstudent;
		newstudent->next = NULL;
	} else {
		while (n > curentstudent->num && curentstudent->next != NULL) {
			laststudent = curentstudent;
			curentstudent = curentstudent->next;
		}
		if (n < curentstudent->num) {
			if (head == curentstudent) { /*�����ͷ*/
				head = newstudent;
				newstudent->next = curentstudent;
			} else { /*�����м�*/
				laststudent->next = newstudent;
				newstudent->next = curentstudent;
			}
		} else { /*�����β*/
			curentstudent->next = newstudent;
			newstudent->next = NULL;
		}
	}
	return (head);
}

/*ɾ�����*/
struct student *delet(struct student *head, char *name) {
	struct student *temp, *p;

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
void printStudent(STUDENT *head) {
	struct student *temp;
	temp = head;
	printf("\nOutput strings:\n");
	while (temp != NULL) {
		printf("\n%d------%s\n", temp->num, temp->name);
		temp = temp->next;
	}
	return;
}
