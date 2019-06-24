<template>
    <div id = "todolistapp">
        <div id = "header" class = "header">
            <h2>투두리스트</h2>
            <input class = "input" type="text" id = "task" placeholder="입력후 엔터"
            v-model.trim="todo"
            v-on:keyup.enter="addTodo">
            <span class = "addbutton" v-on:click="addTodo">추가하기</span>
        </div>
        <ul id = "todolist">
            <li v-for="(a,index) in todolist" :key="a" v-bind:class = "checked(a.done)"
                v-on:click="doneToggle(index)">
                <span>{{a.todo}}</span>
                <span class="close" v-on:click.stop="deleteTodo(index)">&#x00D7;</span>
            </li>
        </ul>
    </div>
</template>
<script>
import axios from 'axios';
export default {
  data() {
    return {
      todo : "",
      todolist : []
    };
  },
  methods: {
      checked : function(done){
        axios.get('/axios/checked')
        .then((response)=>{
            if(done) return{checked:true};
            else return {checked:false};
        })
        .catch((err)=>{
          console.log(err);
        })
      },
      addTodo : function(e){
        axios.get('/axios/addTodo')
        .then((response)=>{
          if(this.todo !=""){
              this.todolist.push({todo:this.todo, done:false});
              this.todo="";
          }
        })
        .catch((err)=>{
          console.log(err);
        })

      },
      deleteTodo : function(index){
          axios.get('/axios/deleteTodo')
          .then((response)=>{
            this.todolist.splice(index,1);
          })
          .catch((err)=>{
            console.log(err);
          })
      },
      doneToggle : function(index){
        axios.get('/axios/doneToggle')
        .then((response)=>{
          this.todolist[index].done = !this.todolist[index].done;
        })
      }
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
 <style>
  * { box-sizing: border-box}
  ul {margin : 0 ; padding : 0}
  ul li {
      cursor: pointer ; position : relative;padding : 8px 8px 8px 40px;
      background:#eee;font-size:14px; transition : 0.2s;
      -webkit-user-select: none;-moz-user-select: none;
      -ms-user-select: none; user-select: none;
  }

  ul li:hover {background:#ddd}
  ul li.checked{background:#BBB;color:#fff;text-decoration: line-through;}

  .close{
      position: absolute ; right: 0 ; top: 0;
  }
  .close:hover{
      background-color: #f44336; color: white
  }
  .header{
      background-color: white ; padding: 30px 30px;
      color : black; text-align: center;
  }
  .header:after{
      content:"";display: table; clear:both;
  }
  .input{
      border:none ; width: 75%; height: 35px; padding:10px;
      float: left; font-size:16px
  }
  .addbutton{
      padding:10px; width:25% ; height: 35px ; background : #d9d9d9;
      color: #555 ; float : left; text-align: center;
      font-size: 13px; cursor: pointer; transition: 0.3s
  }
  .addbutton:hover{background-color:#bbb}
  .completed {text-decoration: none}
</style>
