import React, {Component} from 'react';
import './Waiters.css'
import {WaiterInfo} from "./WaiterInfo";
import {Button} from "@material-ui/core";
import {AddWaiterDialog} from "./AddWaiterDialog";
import {Auth} from "../../../util/Auth";
import axios from 'axios';

export class Waiters extends Component {
    constructor(props) {
        super(props);
        this.state = {
            addDialogOpen: false,
            waiters: [],
            waitersInfo: {}
        }
    }

    componentWillMount() {
        this.loadWaiters()
    }

    loadWaiters() {
        axios.get('/api/listWaitersByInn', {
            params: {
                inn: Auth.getUsername()
            }
        }).then(result => {
            console.log(result.data)
            this.setState({
                waiters: result.data.map(entry => {
                    return {
                        id: entry.id,
                        name: entry.name,
                        rating: null,
                        tips_total: null,
                        cardNumber: entry.cardNumber
                    }
                })
            });
            this.loadWaitersRating()
        });
    }

    loadWaitersRating() {
        axios.get('/api/listFeedbackByInn', {
            params: {
                inn: Auth.getUsername()
            }
        }).then(result => {
            const waiters = {};
            result.data.forEach(entry => {
                const id = entry.waiterId;
                if (id === null) {
                    return
                }
                if (waiters[id] === undefined) {
                    waiters[id] = {
                        rate: 0,
                        cnt: 0
                    }
                }
                waiters[id].rate += entry.rate;
                waiters[id].cnt++;
            });
            for (let key in waiters) {
                if (waiters.hasOwnProperty(key)) {
                    waiters[key] = {
                        avRate: waiters[key].cnt === 0 ? null : waiters[key].rate / waiters[key].cnt
                    }
                }
            }
            this.setState({
                waitersInfo: waiters
            });
        })
    }

    render() {
        return (
            <div className='waiters-main'>
                <div className='waiters-wrapper'>
                    <div className='waiters-add-button'>
                        <Button variant='raised'
                                color='primary'
                                onClick={() => this.setState({addDialogOpen: true})}>
                            Добавить официанта
                        </Button>
                    </div>
                    <div className='waiters-list'>
                        {this.state.waiters.map(waiter =>
                            <WaiterInfo key={waiter.id}
                                        waiterInfo={this.state.waitersInfo[waiter.id]}
                                        waiter={waiter}/>
                        )}
                    </div>
                </div>
                <AddWaiterDialog open={this.state.addDialogOpen}
                                 updateWaiters={() => this.loadWaiters()}
                                 handleClose={() => this.setState({addDialogOpen: false})}/>
            </div>
        )
    }
}