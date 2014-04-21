/*
 * Author: 	Pranav Halkur Umashankar (halkurum@usc.edu)
 *
 * @(#)$Id: my402list.c, v 1.1 2013/09/02 01:18:23 $
 */

#include <stdio.h>
#include <stdlib.h>
#include "my402list.h"
#include "cs402.h"

#define ZERO 0
/*
 * Method to give the number of elements in the list
 * Parameters: list - Pointer to the list
 * Return Value: Integer value giving number of elements in the list
 */
int My402ListLength(My402List* list)
{
	return list->num_members;
}

/*
 * Method to check if the list is empty or not
 * Parameters: list - Pointer to the list
 * Return Values: Integer value specifying if the list is empty or not.
 *	TRUE / 1 - List is empty
 *	FALSE / 0 - List is not empty.
 */
int My402ListEmpty(My402List* list)
{
	if(list->num_members == ZERO)
		return TRUE;
	else 
		return FALSE;
}


/*
 * Method to add the object parameter at the end of the list
 * Parameters:
 *	list - Pointer to the list
 *	obj - The object to be appended
 * Return Values:
 *	TRUE - Obj added successfully to the end of the list
 *	FALSE - Append operation was unsuccessful
 */
int My402ListAppend(My402List* list, void* obj)
{
	// Create a new list element in the heap
	My402ListElem *newListElem, *last;

	newListElem = (My402ListElem *)malloc(sizeof(My402ListElem));

	// Check if memory is successfully allocated
	if(newListElem == NULL)
	{
		// Error : Memory not created for the New list element
		return FALSE;
	}

	// Assign object value to the object of the new List element
	newListElem->obj = obj;
	
	/* When new element is the first element of the list,
	 * 	Make both prev and next pointer of anchor to the element.
	 * 	Make both prev and next pointer of new element point to anchor.
	 * 	Increment the number of members count of the list.
	 *	Remove the pointer link pointed by the newListElem. 
	 */
	if(My402ListEmpty(list))
	{
		list->anchor.next = list->anchor.prev = newListElem;
		newListElem->next = newListElem->prev = (&list->anchor);
		list->num_members++;
		newListElem = NULL;
		return TRUE;
	}

	/* For adding the new element at the end of an already existing list:
	 * 	Get the last element int the list.
   	 *	Point the next pointer of last element and previous pointer of anchor to the new element.
	 *	Point the next pointer of new element to the anchor.
	 *	Point the previous pointer of new element to the last.
 	 * 	Increment the member count of the list.
	 *	Remove the pointer link pointed by last and newListElem.
	 */
	else
	{
		last = My402ListLast(list);
		last->next = list->anchor.prev = newListElem;
		newListElem->next = (&list->anchor);
		newListElem->prev = last;
		list->num_members++;
		last = newListElem = NULL;
		return TRUE;
	}
}


/*
 * Method to add the object parameter at the start of the list
 * Parameters:
 *	list - Pointer to the list
 *	obj - The object to be prepended
 * Return Values:
 *	TRUE - Obj appended successfully to the end of the list
 *	FALSE - Append operation was unsuccessful
 */
int My402ListPrepend(My402List* list, void* obj)
{
	// Create a new list element in the heap
	My402ListElem *newListElem, *first;

	newListElem = (My402ListElem *)malloc(sizeof(My402ListElem));

	// Check if memory is successfully allocated
	if(newListElem == NULL)
	{
		// Error : Memory not created for the New list element
		return FALSE;
	}

	// Assign object value to the object of the new List element
	newListElem->obj = obj;
	
	/* When new element is the first element of the list,
	 * 	Make both prev and next pointer of anchor to the element.
	 * 	Make both prev and next pointer of new element point to anchor.
	 * 	Increment the number of members count of the list. 
	 * 	Remove the pointer link pointed by newListElem.
	 */
	if(My402ListEmpty(list))
	{
		list->anchor.next = list->anchor.prev = newListElem;
		newListElem->next = newListElem->prev = (&list->anchor);
		list->num_members++;
		newListElem = NULL;
		return TRUE;
	}

	/* For adding the new element at the start of an already existing list:
	 * 	Get the first element in the list.
   	 *	Point the previous pointer of first element and next pointer of anchor to the new element.
	 *	Point the previous pointer of new element to the anchor.
	 *	Point the next pointer of new element to the first.
	 * 	Increment the member count of the list. 
	 *	Remove the pointer link pointed by first and newListElem.
	 */
	else
	{
		first = My402ListFirst(list);
		first->prev = list->anchor.next = newListElem;
		newListElem->prev = (&list->anchor);
		newListElem->next = first;
		list->num_members++;
		first = newListElem = NULL;
		return TRUE;
	}
}

/*
 * Method to Unlink and delete the element from the list.
 * Parameters:
 * 	list - Pointer to the list.
 *	listElement - Element to be unlinked and deleted. 
 */
void My402ListUnlink(My402List* list, My402ListElem *listElement)
{
	// Return the control back if the list is empty.
	if(My402ListEmpty(list))
		return;

	/* Remove the links pointed by the list Element and rearrange the remaining list appropriately.
	 * Free the memory pointed by listElement.
	 * Decrement the member count of the list.
	 */
	listElement->prev->next = listElement->next;
	listElement->next->prev = listElement->prev;
	listElement->next = listElement->prev = NULL;
	free(listElement);
	list->num_members--;
}

/*
 * Method to Unlink and delete all elements from the list to make the list empty.
 * Parameter: list - Pointer to the list
 */
void My402ListUnlinkAll(My402List* list)
{
	My402ListElem *current;

	// Return the control back if the list is empty
	if(My402ListEmpty(list))
		return;

	// Assign a current pointer to the first element of the list. 
	current = My402ListFirst(list);
	
	// Loop the list once till the current points to the anchor.
	while(current != (&list->anchor))
	{
		/* Make anchor's next pointer point to the next element after current.
		 * Make next element's previous pointer after current, point to the anchor.
		 * Unlink the current elements prev and next pointers
		 * Free memory pointed by current.
		 * Loop back, making current point to the next element after anchor.
		 */
		list->anchor.next = current->next;
		current->next->prev = (&list->anchor);
		current->next = current->prev = NULL;
		free(current);
		current = list->anchor.next;
	}	
	
	// Unlink the anchor prev and next pointers
	// Set the number of variables of the list to zero since we do not have any more elements in the list.
	// Remove the pointer link pointed by current and listAnchor;
	list->anchor.next = list->anchor.prev = NULL;
	list->num_members = ZERO;
	current = NULL;
}


/*
 * Method to insert object between the given element and the next element.
 * Parameters:
 * 	- list - Pointer to the list.
 * 	- obj - Object of the list element.
 *	- listElement - Element after which the new element with object has to be inserted.
 * Return Values:
 * 	- TRUE if operation is successful
 * 	- FALSE otherwise
 */
int My402ListInsertAfter(My402List* list, void* obj, My402ListElem* listElement)
{
	int returnval;
	My402ListElem *newListElem;	
	
	// If the list is empty or the element to be inserted is null, or the element is the last element, functionality is same as Append.
	if(My402ListEmpty(list) || listElement == NULL || listElement == My402ListLast(list))
	{
		returnval = My402ListAppend(list, obj);
		return returnval;
	}
	
	newListElem = (My402ListElem *)malloc(sizeof(My402ListElem));
	
	// Check if memory is successfully allocated
	if(newListElem == NULL)
	{
		// Error : Memory not created for the New list Element
		return FALSE;
	}	
	
	// Assign object value to the object of the new List Element
	newListElem->obj = obj;
	
	/* Add the new list element after the listElement by rearranging the links of the list.
	 * Increment the member count of the list
	 * Remove memory allocated for newListElem
	 */
	listElement->next->prev = newListElem;
	newListElem->next = listElement->next;
	newListElem->prev = listElement;
	listElement->next = newListElem;
	list->num_members++;
	newListElem = NULL;
	return TRUE;	
}

/*
 * Method to insert object between the given element and the previous element.
 * Parameters:
 * 	- list - Pointer to the list.
 * 	- obj - Object of the list element.
 *	- listElement - Element before which the new element with object has to be inserted.
 * Return Values:
 * 	- TRUE if operation is successful
 * 	- FALSE otherwise
 */
int My402ListInsertBefore(My402List* list, void* obj, My402ListElem* listElement)
{
	int returnval;
	My402ListElem *newListElem;	
	
	// If the list is empty or the element to be inserted is null, or the element is the first element, functionality is same as Prepend.
	if(My402ListEmpty(list) || listElement == NULL || listElement == My402ListFirst(list))
	{
		returnval = My402ListPrepend(list, obj);
		return returnval;
	}	

	newListElem = (My402ListElem *)malloc(sizeof(My402ListElem));
	
	// Check if memory is successfully allocated
	if(newListElem == NULL)
	{
		// Error : Memory not created for the New list Element
		return FALSE;
	}	
	
	// Assign object value to the object of the new List Element
	newListElem->obj = obj;

	/* Insert the new element to the left of the listElement.
	 * Increment member counf of the list.
	 * Remove memory allocated for newListElem.
 	 */
	listElement->prev->next = newListElem;
	newListElem->prev = listElement->prev;
	newListElem->next = listElement;
	listElement->prev = newListElem;
	list->num_members++;
	newListElem = NULL;
	return TRUE;

}

/*
 * Method to return the first element in the list.
 * Parameters: list - Pointer to the list.
 * Return values:
 * 	- Pointer to first element of the list if successful.
 * 	- Null if the list is empty.
 */
My402ListElem* My402ListFirst(My402List* list)
{
	// If list is empty , return null.
	if(My402ListEmpty(list))
		return NULL;
	
	// Return the first element in the list.	
	return list->anchor.next;
}

/*
 * Method to return the last element in the list.
 * Parameters: list - Pointer to the list.
 * Return Values:
 * 	- Pointer to the last element of the list if successful.
 * 	- Null if the list is empty.
 */
My402ListElem* My402ListLast(My402List* list)
{
	// If the list is empty, return null.
	if(My402ListEmpty(list))
		return NULL;

	// Return the last element in the list.
	return list->anchor.prev;
}

/*
 * Method to return the next element given an element.
 * Parameters: 
 *	- list - Pointer to the list
 * 	- listElement - Given element in the list
 * Return Values:
 * 	- listElement->next if operation is successful
 * 	- null if list is empty or listElement is last element in the list.
 */
My402ListElem* My402ListNext(My402List* list, My402ListElem* listElement)
{
	// If the list is empty or listElement is the first element in the list, return null.
	if(My402ListEmpty(list) || (My402ListLast(list) == listElement))
		return NULL;

	return listElement->next;
}


/*
 * Method to return the previous element given an element.
 * Parameters: 
 *	- list - Pointer to the list
 * 	- listElement - Given element in the list
 * Return Values:
 * 	- listElement->prev if operation is successful
 * 	- null if list is empty or listElement is the first element in the list.
 */
My402ListElem* My402ListPrev(My402List *list, My402ListElem* listElement)
{
	// If the list is empty or listElement is the first element in the list, return null.
	if(My402ListEmpty(list) || (My402ListFirst(list) == listElement))
		return NULL;

	return listElement->prev;
}

/*
 * Method to return the list element such that element->obj matches the obj parameter.
 * Parameters:
 * 	- list - Pointer to the list.
 * 	- obj - Ojbect of the element
 * Return Values:
 *  	- list Element if Element->obj = obj
 * 	- null if element is not found or list is empty
 */
My402ListElem* My402ListFind(My402List *list, void *obj)
{
	My402ListElem *current;

	// If the list is empty, return null.
	if(My402ListEmpty(list))
		return NULL;
	
	// Assign current element to first element in the list.
	current = My402ListFirst(list);

	// Loop the list till the end of list is reached
	while(current != (&list->anchor))
	{ 
		// Return the current Element pointer if object matches with object parameter.
		if(current->obj == obj)
			return current;
		
		current = current->next;	
	}
	
	// Remove the pointer link pointed by current.
	// If the end of the list is reached, obj is not present in the list, so return NULL.
	current = NULL;
	return NULL;
}

/*
 * Method to remove the first element in the list
 * Parameters: list - Pointer to the list.
 */
void My402ListRemoveHead(My402List *list)
{
	My402ListElem *head;
	head = My402ListFirst(list);
	My402ListUnlink(list, head);
}

/*
 * Method to initialize the list to an empty list.
 * Paramerters: list - Pointer to the list.
 * Return Values:
 * 	- TRUE if initialization is successful.
 * 	- FALSE if otherwise
 */
int My402ListInit(My402List *list)
{
	// Assign the count of the list and the anchor element
	list->num_members = ZERO;
	list->anchor.next = list->anchor.prev = NULL;	
	
	return TRUE;
}
