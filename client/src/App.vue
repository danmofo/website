<template>
  <div id="app">
    <img src="./images/favicon-196x196.png" alt="" />
    <create-post-form></create-post-form>
  </div>
</template>

<script>
import CreatePostForm from './components/post/CreatePostForm.vue'

export default {
  name: 'app',
  components: {
    'create-post-form': CreatePostForm
  },
  data () {
    return {

    }
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
