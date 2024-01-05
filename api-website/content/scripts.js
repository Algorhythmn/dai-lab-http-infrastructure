document.addEventListener('DOMContentLoaded', () => {
    const todoForm = document.getElementById('todoForm');
    const todoInput = document.getElementById('todoInput');
    const todoList = document.getElementById('todoList');

    // Fetch all todos on page load
    fetchTodos();

    // Function to fetch all todos
    function fetchTodos() {
        fetch('https://localhost/api/todo/all')
            .then(response => response.json())
            .then(todos => {
                displayTodos(todos);
            });
    }

    // Function to display todos
    function displayTodos(todos) {
        todoList.innerHTML = '';
        todos.forEach(todo => {
            const li = document.createElement('li');
            li.textContent = todo.text;

            const deleteBtn = document.createElement('button');
            deleteBtn.textContent = 'Delete';
            deleteBtn.addEventListener('click', () => {
                deleteTodo(todo.id);
            });

            li.appendChild(deleteBtn);
            todoList.appendChild(li);
        });
    }

    // Function to add a new todo
    todoForm.addEventListener('submit', (event) => {
        event.preventDefault();
        const todoText = todoInput.value.trim();

        if (todoText !== '') {
            createTodo({
                text: todoText
            });
            todoInput.value = '';
        }
    });

    // Function to create a new todo
    function createTodo(todoData) {
        fetch('https://localhost/api/todo/', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(todoData)
        })
            .then(response => response.json())
            .then(newTodo => {
                fetchTodos();
            });
    }

    // Function to delete a todo
    function deleteTodo(todoId) {
        fetch(`https://localhost/api/todo/${todoId}`, {
            method: 'DELETE'
        })
            .then(response => {
                fetchTodos();
            });
    }

    // Other functions for updating todos (PUT requests) can be similarly implemented
});
