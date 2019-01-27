import React, { Component } from 'react';
import TextField from '@material-ui/core/TextField';
import '../css/Form.css';

class Form extends Component {
  constructor(props) {
    super(props);
    this.state = {
        courses: 'CIS-197',
        courseHistory: 'CIS-110 CIS-120 CIS-160',
        difficulty: '3',
        mounted: false,
        quality: '3',
        recs: [],
    };

    this.handleCourseChange = this.handleCourseChange.bind(this);
    this.handleCourseHistoryChange = this.handleCourseHistoryChange.bind(this);
    this.handleDifficultyChange = this.handleDifficultyChange.bind(this);
    this.handleQualityChange = this.handleQualityChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.renderRecs = this.renderRecs.bind(this);
  }

  componentDidMount() {
    this.setState({ mounted: true });
  }

  handleCourseChange(event) {
    this.setState({courses: event.target.value});
  }

  handleCourseHistoryChange(event) {
    this.setState({courseHistory: event.target.value});
  }

  handleDifficultyChange(event) {
    this.setState({difficulty: event.target.value});
  }

  handleQualityChange(event) {
    this.setState({quality: event.target.value});
  }

  async handleSubmit(event) {
    event.preventDefault();
    const { courses, difficulty, quality } = this.state;
    const url = `http://localhost:8080/request/?courses=${courses}&diff=${difficulty}&courseQual=${quality}`;
    const response = await fetch(url);
      response.json()
      .then(data => {
        this.setState({ recs: data });
      });
  }

  renderRecs() {
    const { recs } = this.state;
    if (!recs.length) {
        return null;
    }
    return (
      <div className="table">
        <p> Here are some courses you might enjoy! </p>
        <table>
          <tr>
            <th>Course Code</th>
            <th>Course Name</th>
            <th>Course Quality</th>
            <th>Course Difficulty</th>
            <th>Professor Quality</th>
            <th>Course Description</th>
          </tr>
            {recs.map(rec =>
              <tr>
                <td>{rec.code}</td>
                <td>{rec.name}</td>
                <td>{rec.courseQuality}</td>
                <td>{rec.courseDifficulty}</td>
                <td>{rec.profQuality}</td>
                <td>{rec.description}</td>
              </tr>
            )}
        </table>
      </div>
    );
  }

  render() {
    const { mounted } = this.state;
    if (!mounted) return null;
    const recs = this.renderRecs();
    return (
        <div>
          <form onSubmit={this.handleSubmit}>
            <div className="row">
              <label> Courses You Liked </label>
              <TextField className="textField" type="text" multiline value={this.state.courses} onChange={this.handleCourseChange} />
            </div>
            <div className="row">
              <label> Courses You Took </label>
              <TextField className="textField" type="text" multiline value={this.state.courseHistory} onChange={this.handleCourseHistoryChange} />
            </div>
            <div className="row">
              <label> Ideal Course Difficulty </label>
              <select value={this.state.difficulty} onChange={this.handleDifficultyChange}>
                <option value="1">0-1</option>
                <option value="2">1-2</option>
                <option value="3">2-3</option>
                <option value="4">3-4</option>
              </select>
            </div>
            <div className="row">
              <label> Ideal Course Quality </label>
              <select value={this.state.quality} onChange={this.handleQualityChange}>
                  <option value="1">0-1</option>
                  <option value="2">1-2</option>
                  <option value="3">2-3</option>
                  <option value="4">3-4</option>
                </select>
            </div>
            <input type="submit" value="Submit" />
          </form>
          {recs}
        </div>
    );
  }
}

export default Form;