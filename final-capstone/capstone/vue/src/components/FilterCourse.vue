<template>
  <div>

    
    <form   @submit.prevent="submitFilters"  class="filter-form">
      <h2>Find Course</h2>
      <label for="nameInput">Name:</label>
      <input type="text" id="nameInput" v-model="name">
      <label for="stateInput">State Abbr:</label>
      <input type="text" id="stateInput" v-model="state">
      <label for="cityInput">City:</label>
      <input type="text" id="cityInput" v-model="city">
      
      <button type="submit"> <span>Filter</span> </button>
    </form>
    <div v-if="selectedCourse">
      <h2>Selected Course</h2>
      <p>{{ selectedCourse.data.name }} - {{ selectedCourse.data.city }}, {{ selectedCourse.data.state }}</p>
    </div>
    <select-course :filteredCourses="filteredCourses" v-if="showCourseListModal" v-on:close="closeModal" @course-selected="handleCourseSelected" />
    <!-- <div v-if="showCourseListModal" class="modal">
      <div class="modal-content">
        <button class="close-btn" @click="closeCourseListModal">×</button>
        <ul class="course-list">
          <li v-for="course in filteredCourses" :key="course.courseId" class="course-item">
            {{ course.name }} - {{ course.city }}, {{ course.state }}
             <button @click="selectCourse(course)" class="selectCourseButton">Select</button>
          </li>
        </ul>
      </div>
    </div> -->
  </div>
</template>

<script>
import courseService from '../services/CourseService';
import SelectCourse from '../components/SelectCourse.vue'

export default {
  components: {
    SelectCourse
  },
  data() {
    return {
      name: '',
      state: '',
      city: '',
      courses: [],
      selectedCourse: null,
      showForm: true,
      leagues: [],
      leagueMembersNames: [],
      showCourseListModal: false
    };
  },
  computed: {
    filteredCourses() {
      if (this.selectedCourse) {
        return [this.selectedCourse.data];
      } else {
        return this.courses;
      }
    },
  },
  methods: {
    // closeCourseListModal() {
    //   this.showCourseListModal = false;
    // },
    submitFilters() {
      const params = {
        name: this.name,
        state: this.state,
        city: this.city
      };
      courseService.getCoursesByFilter(params)
        .then(courses => {
          this.courses = courses;
          this.selectedCourse = null;
          console.log(courses);
          this.showCourseListModal = true;
        })
        .catch(error => {
          console.error('Error:', error);
        });
    },
    closeModal() {
        this.showCourseListModal = false;
    },
    handleCourseSelected(selectedCourse) {
      this.$emit('toggle-form', selectedCourse)
      
    }
    },
    // selectCourse(course) {
    //   this.selectedCourse = {
    //     id: course.courseId,
    //     data: course
    //   };
    //   console.log(course.courseId);
    //   this.selectedCourseIdLocal = course.courseId;
    //   this.$emit('course-selected', course.courseId);
    //   this.showForm = false;
    //   this.showCourseListModal = false;
    //   this.$emit('toggle-form');
    // },
  // },
}
</script>

<style scoped>

.filter-form {
  display: flex;
  flex-direction: column;
  color: white;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); 
  background: #059262;
   padding: 20px; 
  border-radius: 10px; 
  width: 300px;

}

.filter-form label {
  
}

.filter-form input {
  height: 25px;
  
}

h2{
  margin: 15px;
  display: flex;
  justify-content: center;
  align-items: center;
  
}

button{
  display: inline-block;
  justify-content: center;
  align-items: center;
  align-self: center;
  
  padding: 10px 30px;
  background-color: #ffde59;
  color: white;
  border: none;
  border-radius: 15px; /* Adjust the value to control the roundness */
  cursor: pointer;
  margin-top: 20px;
  margin-bottom: 20px;
  
}

button span{
  font-size: 20px;
  font-weight: bold;
}



button:hover{
   background-color: #fce279;
}

input{
  border-radius: 5px;
  border: none;
  margin-bottom: 10px;
  margin-top: 2px;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}


.modal-content {
  width: 80%;
  max-height: 80%;
  overflow-y: auto;
  background-color: white;
  padding: 20px;
  border-radius: 5px;
  position: relative;
}

.close-btn {
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  font-size: 20px;
  cursor: pointer;
  margin: 0px;
}

.course-list {
  margin: 0;
  padding: 0;
  list-style-type: none;
}

.selectCourseButton {
  padding: 0px;
  margin: 3px;
}

</style>