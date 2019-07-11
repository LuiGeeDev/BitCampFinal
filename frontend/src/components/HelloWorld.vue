<template>
    <div id = "todolistapp"  class="scrollbar scrollbar-primary">
        <div id = "header" class = "header">
            <input class = "input" type="text" id = "task" placeholder="체크리스트 입력"
            v-model.trim="todo"
            v-on:keyup.enter="addTodo">
            <span class = "addbutton" v-on:click="addTodo">추가하기</span>
        </div>
        <ul id = "todolist" class="force-overflow">
            <li v-for="(a,index) in todolist" :key="a" class="li" v-bind:class = "checked(a.checked)"
                v-on:click="doneToggle(index)">
                <span>{{a.content}}</span>
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
      todolist : [],
    };
  },
  mounted(){
    this.todolist=checklist;
  },
  methods: {
      select : function(e){
        axios.get('/axios/select')
        .then((response) => {
          this.todolist = response;
        });
      },
      checked : function(done){
          var cri = false;
          if(done == 1){cri=true;}
          return {checked:cri};
      },
      addTodo : function(e){
        axios.get('/axios/addTodo',{
          params: {
            todo : this.todo,
            group_id : getParameterByName("group_id"),
          },
        })
        .then((response)=>{
          this.todolist.push(response.data.checklist);
          this.todo="";
        })
        .catch((err)=>{
          console.log(err);
        })

      },
      deleteTodo : function(index){
          axios.get('/axios/deleteTodo',{
            params: {
              todo : this.todo,
              group_id : getParameterByName("group_id"),
              index : index,
            }
          })
          .then((response)=>{
            this.todolist.splice(response.data.index,1);
          })
          .catch((err)=>{
            console.log(err);
          })
      },
      doneToggle : function(index){
        axios.get('/axios/doneToggle',{
          params : {
            todolist : this.todolist[index],
            group_id : getParameterByName("group_id"),
            index : index,
          },
        })
        .then((response)=>{
          var resTodo = response.data.checklist;
          var resIndex = response.data.index;
          if(resTodo[resIndex].checked == 0){
            resTodo[resIndex].checked == 1
          }else{
            resTodo[resIndex].checked == 0
          }
          this.todolist[resIndex].checked = resTodo[resIndex].checked;
        })
      },

  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
 <style>
  * { box-sizing: border-box}
  ul {margin : 0 ; padding : 0}
  ul .li {
      cursor: pointer ; position : relative;padding : 8px 8px 8px 40px;
      background:#eee;font-size:14px; transition : 0.2s;
      -webkit-user-select: none;-moz-user-select: none;
      -ms-user-select: none; user-select: none;
  }
  #todolist{
  list-style:none;
  height:25vw;
  overflow-y:scroll;
}
  .li{
  font-family:'Nanum-Gothic';
  font-weight:bold;
  border:1px solid;
  background-color:white !important ;
  margin-bottom:2px;
  padding:10px 0px !important;
  border-radius : 5px;
  }
  ul .li:hover {background:#eee}
  ul .li.checked{background:#ddd;text-decoration: line-through red;}

  .close{
      position: absolute ; right: 0 ; top: 0;
      color:#F39C12;
  }
  .close:hover{
      color: red
  }
  .header{
      background-color: white ; padding: 30px 0px;
      color : black; text-align: center;
      width:100%;
  }
  .header:after{
      content:"";display: table; clear:both;
  }
  .input{
      border:none ; width: 80%; height: 35px; padding:10px;
      float: left; font-size:16px;
      border-radius:5px
  }
  .addbutton{
      padding:10px; width:19% ; height: 35px ; background : #2D3E50;
      color:white;
      float : left; text-align: center;
      font-size: 13px; cursor: pointer; transition: 0.3s;
      font-weight: bold;
      font-family: 'Nanum Gothic';
      border-radius:5px;
      margin-left:1px;
  }
  .addbutton:hover{background-color:#F39C12}
  .completed {text-decoration: none}
  .input{outline:none;border:1px #2D3E50 solid;border-radius:10px;}
</style>
