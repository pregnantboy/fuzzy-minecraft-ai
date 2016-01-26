//
//  RuleInput.h
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 26/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "RuleCondition.h"

@interface RuleInput : NSObject
@property NSString *operator;
@property NSString *equality;
@property RuleCondition *cond;
@end
